package com.example.checklistbtsid.ui.screens.registration

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.checklistbtsid.R
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.AuthBody

@ExperimentalComposeUiApi
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, viewModel: RegistrationViewModel = hiltViewModel(), navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val currentKeyboard = LocalSoftwareKeyboardController.current
    val currentFocus = LocalFocusManager.current
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back))
            }
            Text(text = stringResource(R.string.registration))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = email, onValueChange = { email = it },
            placeholder = { Text(text = stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { currentFocus.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = username, onValueChange = { username = it },
            placeholder = { Text(text = stringResource(R.string.username)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { currentFocus.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    currentFocus.clearFocus()
                    currentKeyboard?.hide()
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
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.registration(AuthBody(email, username, password)){
                when(it){
                    is ResultStatus.SUCCESS -> {
                        Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show()
                    }
                    is ResultStatus.FAILED -> {
                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                    is ResultStatus.EXCEPTION -> {
                        Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }) {
            Text(text = stringResource(id = R.string.registration))
        }
    }
}