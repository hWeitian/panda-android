package com.example.foodpanda_capstone.view.ui.screen
import android.view.MenuItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.foodpanda_capstone.view.ui.theme.NeutralDivider

@Composable
fun HomeScreen(navController: NavController) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp, top=16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(modifier = Modifier.padding(top=8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    CustomCard(width = 180.dp, height = 250.dp, text = "Food Delivery", content = "Big savings on delivery!", R.drawable.food_delivery, navController = navController)
                    CustomCard(width = 180.dp, height = 250.dp, text = "Playlist", content = "Preview/List of playlist", R.drawable.playlist_home_thumbnail, navController = navController)
                }
            }

            item {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Add cards with different sizes
                    CustomCard(width = 180.dp, height = 200.dp, text = "Pandamart", content = "1 for 1 managoes this week", R.drawable.panda_mart, navController = navController)
                    CustomCard(width = 180.dp, height = 200.dp, text = "Shops", content = "Giant, CS Fresh & More", R.drawable.shops, navController = navController)
                }
            }

            item {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CustomCard(width = 180.dp, height = 100.dp, text = "Pick-up", content = "Up to 50% off", R.drawable.pick_up, navController = navController)
                    CustomCard(width = 180.dp, height = 100.dp, text = "Pandago", content = "Send Parcel", R.drawable.panda_go, navController = navController)
                }

            }
            item {
                Row(modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Add cards with different sizes
                    CustomCard(width = 180.dp, height = 100.dp, text = "Dine-in", content = "Up to 50% off Enter Bill", R.drawable.dine_in, navController = navController)
                    CustomCard(width = 180.dp, height = 100.dp, text = "", content = "", R.drawable.ic_arrow_tail_back, navController = navController)
                }

            }


            // Add more cards as needed

//            ContentInsideColumn(color = Color.White, text = "")

            item {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .background(Color.White)) {

                }
            }

            }



}


@Composable
fun CustomCard(width: Dp, height: Dp, text: String, content: String, imgResource: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .width(width)
            .height(height)
            .border(16.dp, Color.White, shape = RoundedCornerShape(16.dp))
            .clickable {
            // Navigate to the desired destination when the card is clicked
            navController.navigate("Playlist List") // Replace with your destination name
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)

        ) {
            Text(text = text, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, style = MaterialTheme.typography.bodyMedium)

            // Image
            Image(
                painter = painterResource(id = imgResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(start = 16.dp)

            )
//        ContentInsideColumn(color = Color.White, text = "")

            Spacer(modifier = Modifier.height(16.dp))
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
        // Your content inside the column
        // You can customize the content based on your requirements
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
