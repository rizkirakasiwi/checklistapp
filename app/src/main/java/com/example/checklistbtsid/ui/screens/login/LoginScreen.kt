package com.example.checklistbtsid.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.checklistbtsid.R
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody
import com.example.checklistbtsid.navigation.Destination
import com.example.checklistbtsid.ui.screens.login.LoginViewModel

@ExperimentalComposeUiApi
@Composable
fun Login(modifier: Modifier = Modifier, viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val currentKeyboard = LocalSoftwareKeyboardController.current
    val currentFocus = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    currentFocus.moveFocus(FocusDirection.Down)
                }
            ),
            placeholder = {
                Text(text = stringResource(R.string.username))
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    currentKeyboard?.hide()
                    currentFocus.clearFocus()
                }
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val passIcon =
                    if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = passIcon,
                        contentDescription = stringResource(R.string.show_password))
                }
            },
            placeholder = {
                Text(text = stringResource(R.string.password))
            })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.login(AuthBody(email = null, username, password)){status->
                when(status){
                    is ResultStatus.SUCCESS -> {
                        Toast.makeText(context, "login Success", Toast.LENGTH_SHORT).show()
                        navController.navigate("${Destination.MAIN}/${status.data?.token}")
                    }
                    is ResultStatus.FAILED -> {
                        Toast.makeText(context, "login Failed", Toast.LENGTH_SHORT).show()
                    }
                    is ResultStatus.EXCEPTION -> {
                        Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                        Log.i("Login", status.exception.message.toString())
                    }
                    else -> {

                    }
                }
            }
        }) {
            Text(text = stringResource(R.string.login))
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = {
            navController.navigate(Destination.REGISTRATION)
        }) {
            Text(text = stringResource(R.string.registration_here))
        }
    }
}