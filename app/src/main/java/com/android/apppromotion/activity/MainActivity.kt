package com.android.apppromotion.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.apppromotion.model.PromoModel
import com.android.apppromotion.ui.theme.MobileAppPromotionTheme
import com.android.apppromotion.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MobileAppPromotionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                TopAppBar(title = {
                                    Text(text = "Promotion App")
                                })
                                MainScreen(viewModel(), navController)
                            }
                        }
                        composable("detail/{itemName}/{itemDesc}/{itemImage}") { backStackEntry ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                TopAppBar(title = {
                                    Text(text = "Detail Promo")
                                })
                                val itemName = backStackEntry.arguments?.getString("itemName").toString()
                                val itemDesc = backStackEntry.arguments?.getString("itemDesc").toString()
                                val itemImage = backStackEntry.arguments?.getString("itemImage").toString()
                                DetailScreen(itemName, itemDesc, itemImage)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(itemName: String, itemDesc: String, itemImage: String) {
    Column(
        modifier = Modifier
            .padding(26.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = itemImage,
                modifier = Modifier
                    .fillMaxWidth() // This will make the Image take full width
                    .height(150.dp) // Dynamic height based on width
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Crop,
                contentDescription = "promo"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = itemName, style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = itemDesc,
            style = TextStyle(color = Color.Gray),
        )
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {
    val isLoading by viewModel.isLoading.collectAsState()
    val items by viewModel.promos.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Box {
                CircularProgressIndicator(
                    color = Color.Green,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
        } else {
            ItemList(items = items, navController)
        }
    }
}

@Composable
fun ItemList(items: List<PromoModel>, navController: NavController) {
    LazyColumn {
        items(items) { item ->
            ItemCard(item = item, navController)
        }
    }
}

@Composable
fun ItemCard(item: PromoModel, navController: NavController) {
    val image = Uri.encode(item.img.formats?.medium?.url.toString())

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    navController.navigate("detail/${item.nama}/${item.desc}/$image")
                           },
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Box {
                    AsyncImage(
                        model = item.img.formats?.medium?.url.toString(),
                        modifier = Modifier
                            .fillMaxWidth() // This will make the Image take full width
                            .height(140.dp) // Dynamic height based on width
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Crop,
                        contentDescription = "promo"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.nama.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                Text(
                    text = item.desc.toString(),
                    style = TextStyle(color = Color.Gray),
                    maxLines = 3
                )
            }
        }
    }
}
