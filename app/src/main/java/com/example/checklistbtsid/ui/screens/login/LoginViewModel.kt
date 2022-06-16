package com.example.checklistbtsid.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody
import com.example.checklistbtsid.model.network.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginServies: LoginServies
):ViewModel() {

    fun login(authBody: AuthBody, response:(ResultStatus<LoginResponse>)->Unit){
        viewModelScope.launch {
            val result = loginServies.login(authBody)
            response(result)
        }
    }
}