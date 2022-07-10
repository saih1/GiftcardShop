package com.example.giftcardshop.view.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giftcardshop.view.ui.theme.Purple700
import com.example.giftcardshop.view.ui.theme.Visibility
import com.example.giftcardshop.view.ui.theme.VisibilityOff

@Composable
fun LoginScreen(
    error: Boolean,
    onLoginClick: (username: String, password: String) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            ClickableText(
                text = AnnotatedString("Sign up here"),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = Purple700
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            val username = rememberSaveable { mutableStateOf("") }
            val password = rememberSaveable { mutableStateOf("") }
            val passwordHidden = rememberSaveable { mutableStateOf(true) }
            val isError = rememberSaveable { mutableStateOf(error) }

            TextField(
                value = username.value,
                onValueChange = {
                    username.value = it
                    isError.value = false
                },
                label = {
                    if (isError.value) Text(text = "Invalid Username", color = MaterialTheme.colors.error)
                    else Text("Username")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = isError.value,
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    isError.value = false
                },
                singleLine = true,
                label = {
                    if (isError.value) Text(text = "Invalid Password", color = MaterialTheme.colors.error)
                    else Text("Password")
                },
                visualTransformation =
                    if (passwordHidden.value) PasswordVisualTransformation()
                    else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordHidden.value = !passwordHidden.value }
                    ) {
                        val visibilityIcon =
                            if (passwordHidden.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordHidden.value) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                },
                isError = isError.value,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        onLoginClick(username.value, password.value)
                    },
                    enabled = username.value.count() > 5 && password.value.count() > 5,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text(text = "Login")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(error = true, onLoginClick = { _, _ -> })
}