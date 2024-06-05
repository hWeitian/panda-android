package com.example.foodpanda_capstone.view.ui.screen

import PREF_KEY_CURRENT_ADDRESS
import PREF_KEY_CURRENT_CITY
import PREF_KEY_CURRENT_ZIPCODE
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.model.AddressRepository
import com.example.foodpanda_capstone.model.Days
import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.timeOfDelivery
import com.example.foodpanda_capstone.view.ui.composable.FoodItemContainerWithoutImage
import com.example.foodpanda_capstone.view.ui.composable.FoodItemDescriptionText
import com.example.foodpanda_capstone.view.ui.composable.FoodItemNameText
import com.example.foodpanda_capstone.view.ui.composable.ImageHolder
import com.example.foodpanda_capstone.view.ui.composable.LargeDropdownMenu
import com.example.foodpanda_capstone.view.ui.composable.LoadingScreen
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.composable.ScreenBottomSpacer
import com.example.foodpanda_capstone.view.ui.composable.SectionTitleAndBtn
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AddressViewModel
import com.example.foodpanda_capstone.viewmodel.GeneralViewModelFactoryDoubleParam
import com.example.foodpanda_capstone.viewmodel.PlaylistViewModel
import getCurrentAddress
import sharedPreferences

@Composable
fun PlaylistConfirmScreen(
    viewModel: PlaylistViewModel,
    navController: NavController,
    selectedAddress: String,
    setAddressOnAppbar: (address: String?, city: String?, zipcode: String?) -> Unit,

) {
    val context = LocalContext.current
    val currentPlaylist by viewModel.currentPlaylist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val daysOfWeek by viewModel.daysOfWeek.collectAsState()
    val selectedTimeOfDelivery by viewModel.selectedTimeOfDelivery.collectAsState()
    val canNavigate by viewModel.canNavigate.observeAsState()

    var selectedIndex by remember { mutableStateOf(-1) }
    var isAddressFormVisible by remember { mutableStateOf(false) }
    // Function to toggle the visibility of the AddressFormScreen
    val toggleAddressFormVisibility = { isAddressFormVisible = !isAddressFormVisible }

    fun Context.getStringSharedPreference(preferenceKey: String): String {
        val sharedPreferences = this.sharedPreferences
        return sharedPreferences.getString(preferenceKey, null) ?: ""
    }

    fun Context.saveAddressToSharedPreferences(address: String, city: String, zipCode: String) {
        val sharedPreferences = this.sharedPreferences
        with(sharedPreferences.edit()) {
            putString(PREF_KEY_CURRENT_ADDRESS, address)
            putString(PREF_KEY_CURRENT_CITY, city)
            putString(PREF_KEY_CURRENT_ZIPCODE, zipCode)
            apply()
        }
    }


    val addressRepository = AddressRepository()
    val addressViewModelFactory = GeneralViewModelFactoryDoubleParam(
        viewModelClass = AddressViewModel::class.java,
        repository = addressRepository,
        factory = ::AddressViewModel,
        context = context,
    )

    val addressFormViewModel: AddressViewModel = viewModel(factory = addressViewModelFactory)

    val currentAddress = context.getCurrentAddress()
//    val savedAddress = context.getStringSharedPreference(PREF_KEY_CURRENT_ADDRESS)
//    val savedZipCode = context.getStringSharedPreference(PREF_KEY_CURRENT_ZIPCODE)
//    val savedCity = context.getStringSharedPreference(PREF_KEY_CURRENT_CITY)

//    var selectedAddress = if (savedAddress != null && savedZipCode != null && savedCity != null) {
//        "$savedAddress $savedZipCode $savedCity"
//    } else {
//        "Type your address here"
//    }

    // Define the setAddressOnUI function
//    val setAddressOnScreen: (String, String, String) -> Unit = { address, city, zipCode ->
//        selectedAddress = "$address, $city, $zipCode"
//        context.saveAddressToSharedPreferences(address, city, zipCode)
//
//    }

    LaunchedEffect(canNavigate) {
        if (canNavigate == true) {
            navController.navigate("Playlists")
        }
    }

    LaunchedEffect(currentPlaylist) {
        val deliveryDay = currentPlaylist.deliveryDay
        val deliveryTime = currentPlaylist.deliveryTime
        if (deliveryDay != "") {
            viewModel.assignDaysOfWeek(deliveryDay)
        }
        if (deliveryTime != "" && deliveryTime != null) {
            viewModel.updateSelectedTimeOfDelivery(deliveryTime)
        }
        if (selectedTimeOfDelivery.isNotBlank() || selectedTimeOfDelivery.isNotEmpty()) {
            selectedIndex = timeOfDelivery.indexOf(selectedTimeOfDelivery)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetData()
            selectedIndex = -1
        }
    }

    if (!isLoading && currentPlaylist !== null) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                SectionTitleAndBtn(
                    title = "Playlist Dishes",
                    btnTitle = "Edit",
                    icon = Icons.Default.RamenDining,
                    modifier = Modifier
                ) {
                    navController.navigate("EditPlaylist/${currentPlaylist.name}")
                }
            }

            items(currentPlaylist.foodItems.orEmpty()) { restaurantFoodItems ->
                if (restaurantFoodItems != null) {
                    Spacer(modifier = Modifier.size(5.dp))
                    restaurantFoodItems.foodItems.mapIndexed { index, item ->
                        FoodItemContainerWithoutImage(item, restaurantFoodItems.restaurantName)
                        if (index != restaurantFoodItems.foodItems.size - 1) {
                            Spacer(
                                modifier = Modifier.size(
                                    dimensionResource(R.dimen.food_item_container_space)
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

// Additional components at the bottom
            item {
                Spacer(modifier = Modifier.size(20.dp))
                SectionTitleAndBtn(
                    title = "Delivery Address",
                    btnTitle = "Edit",
                    icon = Icons.Default.Home,
                    modifier = Modifier
//                    modifier = Modifier.clickable {
//                        toggleAddressFormVisibility()
//                        isAddressFormVisible = true
//                    }
                ) {
                    // TODO: Open edit address bottom sheet
                    toggleAddressFormVisibility()
                    isAddressFormVisible = true
                    Unit
                }
            }


            item {
                Column(modifier = Modifier.padding(8.dp)) {
                    selectedAddress?.let {
                        Text(
                            text = it,
                            style = Typography.bodyLarge
                        )
                    }

                    if (isAddressFormVisible) {
                        AddressFormScreen(
                            addressViewModel = addressFormViewModel,
                            isVisible = isAddressFormVisible,
                            showBottomSheet = isAddressFormVisible,
                            toggleBottomSheet = { toggleAddressFormVisibility() },
                            setAddressOnAppbar = setAddressOnAppbar,
                            currentAddress = currentAddress
                        )

                    }
                }

            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
                SectionTitleAndBtn(
                    title = "Delivery Day & Time",
                    btnTitle = null,
                    icon = Icons.Default.DateRange,
                    modifier = Modifier
                ) { null }
                ClickableDaysOfWeek(
                    daysOfWeek = daysOfWeek,
                    onDayClick = viewModel::onDayClick
                )
            }


            item {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    LargeDropdownMenu(
                        label = "Select Time",
                        items = timeOfDelivery,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index, item ->
                            selectedIndex = index
                            viewModel.updateSelectedTimeOfDelivery(item)
                        },
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
                    Row(
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
                PrimaryButton(
                    name = "Confirm",
                    width = null,
                    isEnabled = selectedTimeOfDelivery.isNotBlank() && viewModel.isAtLeastOneDaySelected(daysOfWeek)
                ) {
                    viewModel.onConfirmSubscriptionClick()
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
        daysOfWeek.forEachIndexed { index, day ->
            Row(modifier = Modifier.weight(1f)) {
                DayOfWeekBox(day.name, index, day.isSelected, onDayClick)
            }
        }
    }
}

@Composable
fun DayOfWeekBox(day: String, index: Int, isSelected: Boolean, onDayClick: (Int) -> Unit) {

    val shape = when (index) {
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
        Text(text = day, color = if (isSelected) Color.White else Color.Black)
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

//@Preview
//@Composable
//fun PlaylistConfirmScreenPreview() {
//    val apiService =  PlaylistApiService
//    val repository = PlaylistRepository(apiService)
//    val viewModel = PlaylistViewModel(repository) // Initialize your ViewModel as needed
//    val navController = rememberNavController() // Initialize NavController as needed
//    PlaylistConfirmScreen(viewModel = viewModel, navController = navController)
//}
