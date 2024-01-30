package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import java.math.BigDecimal

val foodItemConfirm = listOf(
    FoodItem(1, "XL burger", "This is the biggest burger you have ever needed", 1, BigDecimal(12.99), "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    FoodItem(2, "L burger", "This is the biggest burger you have ever needed", 1, BigDecimal(10.99), "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    FoodItem(3, "M burger", "This is the biggest burger you have ever needed", 1, BigDecimal(8.99), "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    FoodItem(4, "S burger", "This is the biggest burger you have ever needed", 1, BigDecimal(6.99), "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    // ... other food items
)
@Composable
fun PlaylistConfirmScreen(foodItem: List<FoodItem>, navController: NavController) {
//fun PlaylistConfirmScreen(viewModel: PlaylistViewModel, navController: NavController) {

//    val currentPlaylist by viewModel.currentPlaylist.collectAsState()


    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "edit",
                    tint = BrandPrimary)
                Spacer(modifier = Modifier.width(4.dp))

                Text(text = "Subscription Details", color = BrandPrimary)

                Spacer(modifier = Modifier.width(150.dp))

//                Text(modifier = Modifier.fillMaxWidth() ,text = "Edit")
                ClickableText(
                    text = AnnotatedString("Edit"),
                    onClick = {
                        // Navigate to the edit playlist screen when the link is clicked
//                        navController.navigate("EditPlaylist")
                    },
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(color = BrandPrimary)
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Most Burger",
                    style = Typography.titleMedium
                )
//                Text(
//                    text = "Burger King",
//                    style = Typography.titleMedium
//                )
            }
        }
        items(foodItem) { foodItem ->
            FoodItemConfirmContainer(foodItem = foodItem)
        }

// Additional components at the bottom
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "address",
                    tint = BrandPrimary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Delivery Address", color = BrandPrimary)
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
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "time",
                    tint = BrandPrimary)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Delivery Time", color = BrandPrimary)
                Spacer(modifier = Modifier.width(8.dp))


            }
            ClickableDaysOfWeek() {
                println("Clicked on $it")
            }
        }

        item {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                DropdownOptionsBox()
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(text = "Delivery will start on the following week after confirmation of payment")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Note: Item(s) will be removed/omitted if is not available on the day.")

                }
            }

        item {
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Total")
                Spacer(modifier = Modifier.width(300.dp))
                Text(text = "S$")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "45.00")
            }
        }

        item {
//            Button(onClick = { /*TODO*/ }) {
//
//            }

            PrimaryButton(name = "Confirm", null) {
                Log.i("Confimed", "Confirmed playlist order")  // TODO: Add Generate Ideas button logic
            }
        }
    }
}
//    }
//}

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
fun ClickableDaysOfWeek(onDayClick: (String) -> Unit) {
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically


    ) {
        items(daysOfWeek) { day ->
            DayOfWeekBox(day, onDayClick)
        }
    }
}

@Composable
fun DayOfWeekBox(day: String, onDayClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp)
            .aspectRatio(1f)
            .clickable { onDayClick(day) }
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .border(1.dp, color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = day, color = Color.Black, fontWeight = FontWeight.Bold)
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

@Preview(showBackground = true)
@Composable
fun PlaylistConfirmScreenPreview() {
    PlaylistConfirmScreen(foodItemConfirm, navController = rememberNavController())
}
