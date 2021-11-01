package com.navico.compose

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.navico.compose.data.Item
import com.navico.compose.ui.theme.NavicoComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val poisLiveData = viewModel.obtainPois(this)
        setContent {
            NavicoComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = getString(R.string.app_name))
                                },
                                navigationIcon = {
//                                    IconButton(onClick = { }) {
//                                        Icon(Icons.Filled.Menu,"")
//                                    }
                                },
                                backgroundColor = Color.Blue,
                                contentColor = Color.White,
                                elevation = 12.dp
                            )
                        }, content = {
                            val myItems: List<Item?> by poisLiveData.observeAsState(listOf())
                            Table(myItems)
//                            Greeting("Android")
                        })
                }
            }
        }
    }

    @Composable
    fun Table(myItems: List<Item?>) {
        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            items(items = myItems, itemContent = { item ->
                when {
                    else -> {
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Title(itemName = item!!.name)
                        }
                        Row {
                            Divider(
                                color = Color.Blue,
                                thickness = 1.dp
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Description(itemDescription = item!!.description)
                        }
                        Row {
                            Divider(
                                color = Color.Blue,
                                thickness = 1.dp
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Address(address = item!!.address)
                        }
                        Row {
                            Divider(
                                color = Color.Black,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            })
        }
    }

    @Composable
    fun Title(itemName: String) {
        Text(
            text = getString(R.string.title)
                .plus(" ")
                .plus(itemName),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun Description(itemDescription: String) {
        Text(text = itemDescription)
    }

    @Composable
    fun Address(address: String) {
        val addressWithTitle = this.getString(R.string.address) + " " + address
        Text(text = addressWithTitle)
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        NavicoComposeTheme {
            Greeting("Android")
        }
    }
}