package com.example.checklistbtsid.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.checklistbtsid.R
import com.example.checklistbtsid.model.data.CheckData
import com.example.checklistbtsid.model.data.ResultStatus
import com.example.checklistbtsid.model.network.CheckBody

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    token: String?,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var check by rememberSaveable {
        mutableStateOf(true)
    }
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }

    val curentToken = "Bearer $token"

    val checkList by viewModel.checkList.collectAsState()
    if (check) {
        if (token != null) {
            viewModel.getList(curentToken)
            check = false
        } else Toast.makeText(context, "Token not found!", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "CheckApp") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new))
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {

        LazyColumn {
            when (checkList) {
                is ResultStatus.SUCCESS -> {
                    items((checkList as ResultStatus.SUCCESS<List<CheckData>>).data!!) { data ->
                        Spacer(modifier = Modifier.height(8.dp))
                        CheckItem(data = data)
                    }
                }
                is ResultStatus.FAILED -> {
                    Toast.makeText(context, "load Failed", Toast.LENGTH_SHORT).show()
                }
                is ResultStatus.EXCEPTION -> {
                    Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

        AddNewLayout(name = name,
            onName = { name = it },
            isOpen = openDialog,
            onOpenChanged = { openDialog = it }) {
            viewModel.addNewList(CheckBody(name), curentToken) {
                when (checkList) {
                    is ResultStatus.SUCCESS -> {
                        viewModel.getList(curentToken)
                    }
                    is ResultStatus.FAILED -> {
                        Toast.makeText(context, "add new check Failed", Toast.LENGTH_SHORT).show()
                    }
                    is ResultStatus.EXCEPTION -> {
                        Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        }
    }
}

@Composable
fun CheckItem(data: CheckData) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(vertical = 8.dp, horizontal = 24.dp)) {
        Text(text = data.name)
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black))
    }
}

@Composable
fun AddNewLayout(
    name: String,
    onName: (String) -> Unit,
    isOpen: Boolean,
    onOpenChanged: (Boolean) -> Unit,
    onAction: () -> Unit,
) {
    if (isOpen) {
        Dialog(onDismissRequest = { onOpenChanged(false) }) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)) {
                TextField(value = name, onValueChange = { onName(it) })
                Row {
                    Button(onClick = {
                        onOpenChanged(false)
                        onAction()
                    }) {
                        Text(text = "OK")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onOpenChanged(false) }) {
                        Text(text = "Batal")
                    }
                }
            }
        }
    }
}