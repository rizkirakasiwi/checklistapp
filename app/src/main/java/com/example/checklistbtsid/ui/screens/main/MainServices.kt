package com.example.checklistbtsid.ui.screens.main

import com.example.checklistbtsid.model.data.CheckData
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.CheckBody
import com.example.checklistbtsid.model.network.CheckListNetwork
import javax.inject.Inject

interface MainServices {
    suspend fun getList(token: String): ResultStatus<List<CheckData>>
    suspend fun addNewList(checkBody: CheckBody, token: String): ResultStatus<CheckData>

}

class MainServiceImpl @Inject constructor(private val checkListNetwork: CheckListNetwork):MainServices{
    override suspend fun getList(token: String): ResultStatus<List<CheckData>> {
        return try {
            val result = checkListNetwork.getList(token)
            if(result.code() == 200){
                ResultStatus.SUCCESS(result.body()?.data)
            }else{
                ResultStatus.FAILED("Failed load list")
            }
        }catch (e : Throwable){
            ResultStatus.EXCEPTION(e)
        }
    }

    override suspend fun addNewList(checkBody: CheckBody, token: String): ResultStatus<CheckData> {
        return try {
            val result = checkListNetwork.addNew(checkBody, token)
            if(result.code() == 200){
                ResultStatus.SUCCESS(result.body()?.data)
            }else{
                ResultStatus.FAILED("Add new list failed")
            }
        }catch (e: Throwable){
            ResultStatus.EXCEPTION(e)
        }
    }

}

