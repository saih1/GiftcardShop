package com.example.giftcardshop.view.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.screens.ProgressBar
import com.example.giftcardshop.view.ui.theme.Visibility
import com.example.giftcardshop.view.ui.theme.VisibilityOff
import com.example.giftcardshop.view.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigateToList: () -> Unit
) {
    val signInStatus by loginViewModel.signInStatus.collectAsState()
    val attempts = remember { mutableStateOf(0) }
    val isError = attempts.value > 0

    when (signInStatus.status) {
        Status.SUCCESS -> {
            if (signInStatus.data == false) {
                LoginComposable(
                    error = isError,
                    onLoginClick = { username, password ->
                        loginViewModel.signIn(username, password)
                        attempts.value ++
                    }
                )
            } else if (signInStatus.data == true) {
                attempts.value = 0
                navigateToList()
            }
        }
        Status.LOADING -> ProgressBar()
        Status.ERROR -> {
            LoginComposable(
                error = true,
                onLoginClick = { username, password ->
                    loginViewModel.signIn(username, password)
                }
            )
        }
        else -> {}
    }
}

@Composable
fun LoginComposable(
    error: Boolean,
    onLoginClick: (username: String, password: String) -> Unit
) {
    val username = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordHidden = rememberSaveable { mutableStateOf(true) }
    val isError = rememberSaveable { mutableStateOf(error) }

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
                    color = MaterialTheme.colors.primary
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            TextField(
                value = username.value,
                onValueChange = {
                    username.value = it
                    isError.value = false
                },
                label = {
                    if (isError.value) Text(
                        text = "Invalid Username",
                        color = MaterialTheme.colors.error
                    ) else Text(
                        text = "Username"
                    )
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
                    if (isError.value) Text(
                        text = "Invalid Password",
                        color = MaterialTheme.colors.error
                    ) else Text("Password")
                },
                visualTransformation =
                    if (passwordHidden.value) PasswordVisualTransformation()
                    else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordHidden.value = !passwordHidden.value }
                    ) {
                        Icon(
                            imageVector =
                                if (passwordHidden.value) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                            contentDescription =
                                if (passwordHidden.value) "Show password"
                                else "Hide password"
                        )
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
                    fontFamily = FontFamily.Default,
                    color = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreenError() {
    LoginComposable(error = true, onLoginClick = { _, _ -> })
}

@Preview
@Composable
fun PreviewLoginScreenDefault() {
    LoginComposable(error = false, onLoginClick = { _, _ -> })
}