package com.example.checklistbtsid.model.network

import com.example.checklistbtsid.model.data.EmptyResponse
import com.example.checklistbtsid.model.data.RespondNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

data class AuthBody(
    val email: String?,
    val username: String,
    val password: String
)

data class LoginResponse(
    val token:String
)

interface AuthNetwork {
    @POST("login")
    suspend fun login(@Body data:AuthBody ):Response<RespondNetwork<LoginResponse>>

    @POST("register")
    suspend fun register(@Body data:AuthBody ):Response<RespondNetwork<EmptyResponse>>
}

class AuthImpl @Inject constructor(private val retrofit:Retrofit):AuthNetwork{
    private val auth = retrofit.create(AuthNetwork::class.java)
    override suspend fun login(data: AuthBody): Response<RespondNetwork<LoginResponse>> {
        return withContext(Dispatchers.IO){
            auth.login(data)
        }
    }

    override suspend fun register(data: AuthBody): Response<RespondNetwork<EmptyResponse>> {
        return withContext(Dispatchers.IO){
            auth.register(data)
        }
    }

}

