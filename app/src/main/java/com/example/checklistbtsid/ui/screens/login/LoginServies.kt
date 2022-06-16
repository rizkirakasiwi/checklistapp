package com.example.checklistbtsid.ui.screens.login

import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody
import com.example.checklistbtsid.model.network.AuthNetwork
import com.example.checklistbtsid.model.network.LoginResponse
import javax.inject.Inject

interface LoginServies {
    suspend fun login(authBody: AuthBody):ResultStatus<LoginResponse>
}

class LoginServiceImpl @Inject constructor(
    private val authNetwork: AuthNetwork
):LoginServies{
    override suspend fun login(authBody: AuthBody): ResultStatus<LoginResponse> {
        return try {
            val result = authNetwork.login(authBody)
            if(result.code() == 200){
                ResultStatus.SUCCESS(result.body()?.data)
            }else{
                ResultStatus.FAILED("Login Failed")
            }
        }catch (e:Throwable){
            ResultStatus.EXCEPTION(e)
        }
    }

}

