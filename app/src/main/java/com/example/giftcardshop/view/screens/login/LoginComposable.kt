package com.example.giftcardshop.view.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.giftcardshop.domain.model.AuthStatus

@Composable
fun LoginScreen(
    onLogin: (username: String, password: String) -> Unit,
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = username.value,
                onValueChange = { username.value = it },
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
            )
            Button(
                onClick = {
                    onLogin(username.value, password.value)
                },
                enabled = username.value.isNotBlank() && password.value.isNotBlank()
            ) {
                Text(text = "Login")
            }
        }
    }

}