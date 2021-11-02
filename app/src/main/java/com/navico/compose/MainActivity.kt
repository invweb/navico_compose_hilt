package com.navico.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.navico.compose.data.Item
import com.navico.compose.ui.theme.NavicoComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val ctx: Context = this

    private var content: Item? = null

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val poisLiveData = viewModel.obtainPois(this)
        setContent {
            NavicoComposeTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = getString(R.string.app_name))
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        if(counter > 0) {
                                            counter--
                                            navController.popBackStack()
                                        }
                                    }) {
                                        Icon(Icons.Filled.ArrowBack,"")
                                    }
                                },
                                backgroundColor = Color.Blue,
                                contentColor = Color.White,
                                elevation = 12.dp
                            )

                        }, content = {
                            val myItems: List<Item?> by poisLiveData.observeAsState(listOf())

                            NavHost(
                                navController = navController,
                                startDestination = "table_composable"
                            ) {
                                composable("item_composable") {
                                    ItemComposable(navController)
                                }
                                composable("table_composable") {
                                    TableComposable(navController, myItems)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    private fun ItemComposable(navController: NavHostController) {
        counter++
        Scaffold(modifier = Modifier.padding(16.dp), content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(content!!.photo),
                    contentDescription = null,
                    modifier = Modifier.size(256.dp)
                )
                Text(style = typography.h6,
                    text = content!!.name)
                Text(text = content!!.address)
                Text(text = content!!.description)
            }
        })
    }

    @Composable
    fun TableComposable(navController: NavHostController, myItems: List<Item?>) {
        if(counter > 0) {
            counter--
        }
        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            items(items = myItems, itemContent = { item ->
                Column(
                    modifier = Modifier.clickable(
                        onClick = {
                            Toast.makeText(ctx, "click", Toast.LENGTH_LONG).show()

                            content = item
                            navController.navigate("item_composable")
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Title(itemName = item!!.name)
                    }
                    Row {
                        Divider(
                            color = Color.Black,
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
                            color = Color.Black,
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
                            color = Color.Blue,
                            thickness = 1.dp
                        )
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
}