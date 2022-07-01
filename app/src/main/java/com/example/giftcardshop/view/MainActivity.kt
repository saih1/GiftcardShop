package com.example.giftcardshop.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.shared.Constants.TAG
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.Status
import com.example.giftcardshop.view.giftcard_list.GiftcardListViewModel
import com.example.giftcardshop.view.ui.theme.GiftcardShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listViewModel: GiftcardListViewModel by viewModels()
        setContent {
            // TESTING
            listViewModel.giftcards.collectAsState().value.also { requestState ->
                if (requestState.status == Status.ERROR) {
                    Toast.makeText(this, requestState.message, Toast.LENGTH_SHORT).show()
                } else if (requestState.status == Status.SUCCESS) {
                    Toast.makeText(this, "SUCCESS!", Toast.LENGTH_SHORT).show()
                    requestState.data?.forEach {
                        Log.i(TAG, " ${it.brand}")
                    }
                }
            }
            GiftcardShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GiftcardShopTheme {
        Greeting("Android")
    }
}