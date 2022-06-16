package com.example.checklistbtsid.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checklistbtsid.model.data.CheckData
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.CheckBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainServices: MainServices): ViewModel() {
    private val _checkList = MutableStateFlow<ResultStatus<List<CheckData>>>(ResultStatus.NOTHING())
    val checkList = _checkList.asStateFlow()

    fun getList(token:String){
        viewModelScope.launch {
            val result = mainServices.getList(token)
            _checkList.emit(result)
        }
    }

    fun addNewList(data:CheckBody, token: String, response:(ResultStatus<CheckData>) -> Unit){
        viewModelScope.launch {
            val result = mainServices.addNewList(data,token)
            response(result)
        }
    }

}