package com.example.checklistbtsid.model.network

import com.example.checklistbtsid.model.data.CheckData
import com.example.checklistbtsid.model.data.RespondNetwork
import com.example.checklistbtsid.model.data.ResponsesNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject

data class CheckBody(val name:String)
interface CheckListNetwork {
    @POST("checklist")
    suspend fun addNew(@Body checkBody: CheckBody,  @Header("Authorization") token:String):Response<RespondNetwork<CheckData>>

    @GET("checklist")
    suspend fun getList( @Header("Authorization") token:String):Response<ResponsesNetwork<CheckData>>
}

class CheckListNetworkImpl @Inject constructor(private val retrofit: Retrofit):CheckListNetwork{
    private val check = retrofit.create(CheckListNetwork::class.java)
    override suspend fun addNew(
        checkBody: CheckBody,
        token: String,
    ): Response<RespondNetwork<CheckData>> {
        return withContext(Dispatchers.IO){
            check.addNew(checkBody, token)
        }
    }

    override suspend fun getList(token: String): Response<ResponsesNetwork<CheckData>> {
        return withContext(Dispatchers.IO){
            check.getList(token)
        }
    }


}