package com.example.checklistbtsid.ui.screens.registration

import com.example.checklistbtsid.model.data.EmptyResponse
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody
import com.example.checklistbtsid.model.network.AuthNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface RegistrationService {
    suspend fun registration(data:AuthBody):ResultStatus<EmptyResponse?>
}

class RegistrationServiceImpl @Inject constructor(
    private val authNetwork: AuthNetwork
):RegistrationService{
    override suspend fun registration(data: AuthBody): ResultStatus<EmptyResponse?> {
        return try{
            val result = authNetwork.register(data)
            if(result.code() == 200){
                ResultStatus.SUCCESS(result.body()?.data)
            }else{
                ResultStatus.FAILED(result.body()?.message)
            }
        }catch (e:Throwable){
            ResultStatus.EXCEPTION(e)
        }
    }

}
