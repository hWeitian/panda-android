package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier,
            ) {
                Card(
                    modifier = Modifier
                        .width(480.dp)
                        .height(150.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    onClick = { navController.navigate("Playlists") }
                ) {
                    HorizontalCardContent(
                        text = "Food Playlist",
                        content = "Build your mix!",
                        imgResource = R.drawable.food_playlist_2,
                        imgHeight = 350.dp,
                        modifier = Modifier,
                    )
                }
            }

        CardRow(
            leftCardContent = {
                CardContent(
                    text = "Food Delivery",
                    content = "Big savings on delivery!",
                    imgResource = R.drawable.food_delivery,
                    imgHeight = 95.dp,
                    modifier = Modifier
                )
            },
            rightCardContent = {
                CardContent(
                    text = "Dine-in",
                    content = "Up to 50% off entire bill",
                    imgResource = R.drawable.dine_in,
                    imgHeight = 250.dp,
                    modifier = Modifier.aspectRatio(1f)
                )
            },
            navController = navController,
            leftCardHeight = 180.dp,
            rightCardHeight = 180.dp
        )

        CardRow(
            leftCardContent = {
                CardContent(
                    text = "pandamart",
                    content = "Fresh groceries & more",
                    imgResource = R.drawable.panda_mart,
                    imgHeight = 85.dp,
                    modifier = Modifier,
                )
            },
            rightCardContent = {
                CardContent(
                    text = "Shops",
                    content = "Giant, CS Fresh & More",
                    imgResource = R.drawable.shops,
                    imgHeight = 90.dp,
                    modifier = Modifier,
                )
            },
            navController = navController,
            leftCardHeight = 180.dp,
            rightCardHeight = 180.dp
        )

        CardRow(
            leftCardContent = {
                HorizontalCardContent(
                    text = "Pick-up",
                    content = "Up to 50% off",
                    imgResource = R.drawable.pick_up,
                    imgHeight = 120.dp,
                    modifier = Modifier,
                )
            },
            rightCardContent = {
                HorizontalCardContent(
                    text = "pandago",
                    content = "Send parcels",
                    imgResource = R.drawable.panda_go,
                    imgHeight = 100.dp,
                    modifier = Modifier,
                )
            },
            navController = navController,
            leftCardHeight = 100.dp,
            rightCardHeight = 100.dp
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRow(
    leftCardContent: @Composable () -> Unit,
    rightCardContent: @Composable () -> Unit,
    navController: NavController,
    leftCardHeight: Dp,
    rightCardHeight: Dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .height(leftCardHeight)
                .weight(1f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            onClick = { navController.navigate("Playlists") }
        ) {
            leftCardContent()
        }
        CardSpacer()
        Card(
            modifier = Modifier
                .height(rightCardHeight)
                .weight(1f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            onClick = { navController.navigate("Playlists") }
        ) {
            rightCardContent()
        }
    }
}


@Composable
fun CardContent(
    text: String,
    content: String,
    imgResource: Int,
    imgHeight: Dp,
    modifier: Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = text, style = Typography.titleSmall)
            Text(text = content, style = Typography.bodyMedium)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = imgResource),
                contentDescription = null,
                modifier = modifier
                    .height(imgHeight)
            )
        }
    }
}

@Composable
fun HorizontalCardContent(
    text: String,
    content: String,
    imgResource: Int,
    imgHeight: Dp,
    modifier: Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = text, style = Typography.titleSmall)
            Text(text = content, style = Typography.bodyMedium)
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Image(
                painter = painterResource(id = imgResource),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .height(imgHeight)
            )
        }
    }
}


@Composable
fun CardSpacer() {
    Spacer(modifier = Modifier.width(10.dp))
}

@Composable
fun ContentInsideColumn(color: Color, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color)
    ) {

    }
}


@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItems>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItems) -> Unit,

    ) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface {
//        HomeScreen(navController)
    }

}
