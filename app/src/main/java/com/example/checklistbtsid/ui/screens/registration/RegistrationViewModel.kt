package com.example.checklistbtsid.ui.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checklistbtsid.model.data.EmptyResponse
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val service : RegistrationService
):ViewModel() {

    fun registration(data:AuthBody, response: (ResultStatus<EmptyResponse?>) -> Unit){
        viewModelScope.launch {
            val result = service.registration(data)
            response(result)
        }
    }
}