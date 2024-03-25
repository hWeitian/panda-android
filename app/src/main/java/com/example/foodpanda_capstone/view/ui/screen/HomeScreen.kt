package com.example.foodpanda_capstone.view.ui.screen

import android.view.MenuItem
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.theme.NeutralDivider
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp, top = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier,
        ) {
            CustomCardHorizontal(
                width = 480.dp,
                height = 150.dp,
                text = "Food Playlist",
                content = "Build your mix!",
                imgResource = R.drawable.food_playlist_2,
                imgHeight = 350.dp,
                navController = navController
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomCard(
                width = 180.dp,
                height = 180.dp,
                text = "Food Delivery",
                content = "Big savings on delivery!",
                R.drawable.food_delivery,
                imgHeight = 100.dp,
                modifier = Modifier,
                navController = navController
            )
            CustomCard(
                width = 180.dp,
                height = 180.dp,
                text = "Dine-in",
                content = "Up to 50% off entire bill",
                imgResource = R.drawable.dine_in,
                imgHeight = 250.dp,
                modifier = Modifier.aspectRatio(1f),
                navController = navController
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomCard(
                width = 180.dp,
                height = 180.dp,
                text = "pandamart",
                content = "Fresh groceries & more",
                imgResource = R.drawable.panda_mart,
                imgHeight = 100.dp,
                modifier = Modifier,
                navController = navController
            )
            CustomCard(
                width = 180.dp,
                height = 180.dp,
                text = "Shops",
                content = "Giant, CS Fresh & More",
                imgResource = R.drawable.shops,
                imgHeight = 100.dp,
                modifier = Modifier,
                navController = navController
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomCardHorizontal(
                width = 180.dp,
                height = 100.dp,
                text = "Pick-up",
                content = "Up to 50% off",
                imgResource = R.drawable.pick_up,
                imgHeight = 100.dp,
                navController = navController
            )
            CustomCardHorizontal(
                width = 180.dp,
                height = 100.dp,
                text = "pandago",
                content = "Send parcels",
                imgResource = R.drawable.panda_go,
                imgHeight = 100.dp,
                navController = navController
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
    }

}


@Composable
fun CustomCard(
    width: Dp,
    height: Dp,
    text: String,
    content: String,
    imgResource: Int,
    imgHeight: Dp,
    modifier: Modifier,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .border(16.dp, Color.White, shape = RoundedCornerShape(16.dp))
            .clickable { navController.navigate("Playlists") },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
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
}

@Composable
fun CustomCardHorizontal(
    width: Dp,
    height: Dp,
    text: String,
    content: String,
    imgResource: Int,
    imgHeight: Dp,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .border(16.dp, Color.White, shape = RoundedCornerShape(16.dp))
            .clickable { navController.navigate("Playlists") },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
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
