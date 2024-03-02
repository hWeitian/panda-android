package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.model.Days
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.LargeDropdownMenu
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.RestaurantNameText
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import com.google.android.material.color.MaterialColors.ALPHA_DISABLED
import com.google.android.material.color.MaterialColors.ALPHA_FULL
import java.math.BigDecimal

@Composable
fun PlaylistConfirmScreen(viewModel: PlaylistViewModel, navController: NavController) {

    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val daysOfWeek by viewModel.daysOfWeek.collectAsState()
    val timeOfDelivery by viewModel.timeOfDelivery.collectAsState()

    var selectedIndex by remember { mutableStateOf(-1) }

    if (!isLoading && currentPlaylist !== null) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                SectionTitleAndBtn(
                    title = "Subscription Details",
                    btnTitle = "Edit",
                    icon = Icons.Default.Loyalty
                ) {
                    navController.navigate("View")
                }
            }

            items(currentPlaylist.foodItems.orEmpty()) { restaurantFoodItems ->
                if (restaurantFoodItems != null) {
                    Spacer(modifier = Modifier.height(20.dp))
                    RestaurantNameText(restaurantFoodItems.restaurantName)
                    restaurantFoodItems.foodItems.map { item ->
                        FoodItemContainer(item)
                    }
                }
            }

// Additional components at the bottom
            item {
                Spacer(modifier = Modifier.size(20.dp))
                SectionTitleAndBtn(
                    title = "Delivery Address",
                    btnTitle = "Edit",
                    icon = Icons.Default.Home
                ) {
                    // TODO: Open edit address bottom sheet
                    // navController.navigate("View")
                }
            }


            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Address 1 xxxxx")
                    Text(text = "Address 2 xxxxx")
                    Text(text = "Address 3 xxxxx")
                }

            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
                SectionTitleAndBtn(
                    title = "Delivery Day & Time",
                    btnTitle = null,
                    icon = Icons.Default.DateRange
                ) { null }
                ClickableDaysOfWeek(
                    daysOfWeek = daysOfWeek,
                    onDayClick = viewModel::onDayClick
                )
            }


            item {
                Column(modifier = Modifier.padding(top = 8.dp)) {
//                    DropdownOptionsBox()

                    LargeDropdownMenu(
                        label = "Select Time",
                        items = timeOfDelivery,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index, _ -> selectedIndex = index },
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        text = "Note:",
                        style = Typography.bodyMedium
                    )
                    Text(
                        text = "Delivery begins the week after payment confirmation.",
                        style = Typography.bodyMedium
                    )
                    Text(
                        text = "Item(s) will be removed if is not available on the day.",
                        style = Typography.bodyMedium
                    )

                }
            }

            item {
                Spacer(modifier = Modifier.size(25.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total",
                            style = Typography.titleMedium
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            text = "(incl. GST where applicable)",
                            style = Typography.bodySmall
                        )
                    }
                    Text(
                        text = "S$ ${"%.2f".format(currentPlaylist.cost)}",
                        style = Typography.titleMedium
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.size(15.dp))
                PrimaryButton(name = "Confirm", null) {
                    Log.i("Confimed", "Confirmed playlist order")
                    // TODO: Add POST function to send playlist data to backend
                }
                ScreenBottomSpacer()
            }
        }
    } else {
        LoadingScreen()
    }
}


@Composable
fun FoodItemConfirmContainer(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = BrandSecondary,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1f,
                )
            }
            .padding(bottom = 10.dp, top = 18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .width(250.dp)
                    .height(80.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    FoodItemNameText(foodItem.name)
                    FoodItemDescriptionText(foodItem.description)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Qty: ${foodItem.quantity}",
                        style = Typography.bodyMedium,
                        color = BrandDark
                    )
                    Text(
                        text = "S$ ${"%.2f".format(foodItem.price)}",
                        style = Typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            ImageHolder(imageUrl = foodItem.imageUrl, height = 80, description = foodItem.name)
        }
    }
}


@Composable
fun ClickableDaysOfWeek(daysOfWeek: List<Days>, onDayClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        daysOfWeek.forEachIndexed {index, day ->
            Row(modifier = Modifier.weight(1f)) {
                DayOfWeekBox(day.name, index, day.isSelected, onDayClick)
            }
        }
    }
}

@Composable
fun DayOfWeekBox(day: String, index: Int, isSelected: Boolean, onDayClick: (Int) -> Unit) {

    val shape = when(index) {
        0 -> RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
        6 -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
        else -> RoundedCornerShape(0.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onDayClick(index) }
            .background(color = if (isSelected) BrandPrimary else Color.White, shape = shape)
//            .border(1.dp, color = if(index == 0 || index == 6) Color.Black else Color.Black.copy(alpha = 1f), shape = shape),
            .border(1.dp, color = Color.Black, shape = shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = day, color = if(isSelected) Color.White else Color.Black)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownOptionsBox() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Option 1") }

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { /* Handle value change if needed */ },
            readOnly = true,
            label = { Text("Select an option") },
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, color = Color.Black),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedOption = option
                        expanded = false
                    }, text = { Text(option) })
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PlaylistConfirmScreenPreview() {
//    PlaylistConfirmScreen(foodItemConfirm, navController = rememberNavController())
//}
