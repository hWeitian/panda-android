package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.foodpanda_capstone.model.AddressData
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.example.foodpanda_capstone.viewmodel.AddressViewModel
import setSharedPreferenceAddressData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressFormScreen(
    addressViewModel: AddressViewModel,
//    navController: NavController,
    isVisible: Boolean,
//    onDismissRequest: () -> Unit
    showBottomSheet: Boolean,
    toggleBottomSheet: () -> Unit,
    setAddressOnAppbar: (address: String?, city: String?, zipcode: String?) -> Unit,
    currentAddress: AddressData
) {
    val addresses by addressViewModel.addresses.collectAsState()
    val isLoading by addressViewModel.isLoading.collectAsState()
    var isAddressInputFormVisible by remember { mutableStateOf(false) }
    var editedAddress by remember { mutableStateOf<AddressData?>(null) } // Track edited address

    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        println("At address isVisible")
        addressViewModel.getInitialAddress()
    }

    DisposableEffect(Unit) {
        onDispose { addressViewModel.deleteAll() }
    }

    if (!isLoading) {
        ModalBottomSheet(
            onDismissRequest = { toggleBottomSheet() },
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                addresses.map { address ->
                    println("address: $address")
                    RoundCheckBoxWithText(
                        index = addresses.indexOf(address),
                        address = address,
                        isAddressSelected = address.isSelected,
                        editedAddress = editedAddress,
                        viewModel = addressViewModel,
                        toggleBottomSheet = toggleBottomSheet,
                        setAddressOnAppbar = setAddressOnAppbar,
                    )
                }
//                Spacer(modifier = Modifier.height(16.dp))

                // Clickable text with an icon
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .padding(start = 15.dp, top = 0.dp, end = 0.dp, bottom = 30.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            isAddressInputFormVisible = true
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add address",
                        modifier = Modifier.size(24.dp),
                        tint = BrandPrimary
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "Add your address here",
                        color = BrandPrimary,
                        style = Typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (isAddressInputFormVisible) {
                AddressInputForm(
                    viewModel = addressViewModel,
                    onDismissRequest = { isAddressInputFormVisible = false },
                    selectedAddress = editedAddress,
                    setAddressOnAppbar = setAddressOnAppbar,
                    toggleBottomSheet = toggleBottomSheet,
                )
            }
        }

    }
}

@Composable
fun AddressInputForm(
    viewModel: AddressViewModel,
    onDismissRequest: () -> Unit,
    selectedAddress: AddressData?,
    toggleBottomSheet: () -> Unit,
    setAddressOnAppbar: (String?, String?, String?) -> Unit,
) {
    var address by remember { mutableStateOf(selectedAddress?.address ?: "") }
    var city by remember { mutableStateOf(selectedAddress?.city ?: "") }
    var zipCode by remember { mutableStateOf(selectedAddress?.zipCode ?: "") }

    val primaryButtonText: String = if (selectedAddress != null) {
        "Edit"
    } else {
        "Add"
    }

    val titleText = if (selectedAddress != null) {
        "Edit address"
    } else {
        "Add address"
    }


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
//                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(25.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                    ) {
                        Text(text = titleText, style = Typography.titleSmall)
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close button",
                            tint = BrandDark,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onDismissRequest() }
                        )
                    }
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Street") },
                        textStyle = TextStyle(fontWeight = FontWeight.Normal)
                    )
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        textStyle = TextStyle(fontWeight = FontWeight.Normal)
                    )
                    OutlinedTextField(
                        value = zipCode,
                        onValueChange = { zipCode = it },
                        label = { Text("Zip Code") },
                        textStyle = TextStyle(fontWeight = FontWeight.Normal)
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))
                PrimaryButton(
                    name = primaryButtonText,
                    width = null) {
                    val newAddress = AddressData(address, city, zipCode)
                    if (selectedAddress != null) {
                        viewModel.updateAddress(selectedAddress, newAddress)
                    } else {
                        viewModel.saveAddress(newAddress)
                    }
                    onDismissRequest()
                }
            }

        }
    }

//    AlertDialog(
//        onDismissRequest = { onDismissRequest() },
//        title = {
//            // Title of the AlertDialog
//            Text("Add / Edit your address here.", fontSize = 15.sp)
//        },
//        text = {
//            // Content of the AlertDialog
//            Column {
//                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Street") })
//                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
//                OutlinedTextField(value = zipCode, onValueChange = { zipCode = it }, label = { Text("Zip Code") })
//            }
//        },
//        confirmButton = {
//            // Confirm button in the AlertDialog
//            Row() {
//                Button(
//                    onClick = {
//                        //address from input which has yet to be saved. If the address from database is not null, update old with new address.
//                        //Else just save into database with the new input address.
//                        val newAddress = AddressData(address, city, zipCode)
//                        if (selectedAddress != null) {
//                            viewModel.updateAddress(selectedAddress, newAddress)
//                        } else {
//                            viewModel.saveAddress(newAddress)
//                        }
//
//                        onDismissRequest()
//                    }
//                ) {
//                    if (selectedAddress != null) {
//                        Text("Edit", modifier = Modifier.padding(start = 16.dp, end = 16.dp))
//                    } else {
//                        Text("Add", modifier = Modifier.padding(start = 16.dp, end = 16.dp))
//                    }
//                }
//
//                Spacer(modifier = Modifier.padding(4.dp))
//                Button(
//                    onClick = {
//                        //address from input which has yet to be saved. If the address from database is not null, update old with new address.
//                        //Else just save into database with the new input address.
//                        viewModel.removeAddress(AddressData(address, city, zipCode))
//                        onDismissRequest()
//                    }
//                ) {
//                    Text("Remove")
//                }
//            }
//
//        }
//    )
}

@Composable
fun RoundCheckBoxWithText(
    index: Int, // Added index parameter
    address: AddressData,
    isAddressSelected: Boolean,
    viewModel: AddressViewModel,
    editedAddress: AddressData?,
    toggleBottomSheet: () -> Unit, // Added toggleBottomSheet paramet
    setAddressOnAppbar: (String?, String?, String?) -> Unit,
) {
    val context = LocalContext.current

    var isAddressInputFormVisible by remember { mutableStateOf(false) }
    var editAddress by remember { mutableStateOf(editedAddress) }

    Row(
        modifier = Modifier
            .padding(start = 0.dp, top = 0.dp, end = 10.dp, bottom = 20.dp),
        verticalAlignment = CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(0.9f)
                .clickable {
                    viewModel.selectAddress(address)
                    toggleBottomSheet()
                    setAddressOnAppbar(address.address, address.city, address.zipCode)
                }
        ) {
            RadioButton(
                selected = isAddressSelected,
                onClick = {
                    viewModel.selectAddress(address)
                    toggleBottomSheet()
                    setAddressOnAppbar(address.address, address.city, address.zipCode)
                }
            )
            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = "Street: ${address.address}",
                    style = Typography.bodyLarge
                )
                Text(
                    text = "City: ${address.city}",
                    style = Typography.bodyLarge
                )
                Text(
                    text = "Zip Code: ${address.zipCode}",
                    style = Typography.bodyLarge
                )
            }
        }
        Row (
            modifier = Modifier.weight(0.2f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Edit Address",
                modifier = Modifier
                    .weight(0.1f)
                    .size(20.dp)
                    .clickable {
                        viewModel.removeAddress(AddressData(address.address, address.city, address.zipCode))
                        setAddressOnAppbar(null, null, null)
//                        onDismissRequest()
                    }
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Address",
                modifier = Modifier
                    .weight(0.1f)
                    .size(20.dp)
                    .clickable {
                        editAddress = AddressData(address.address, address.city, address.zipCode)
                        isAddressInputFormVisible = true
                    }
            )

        }



    }

    if (isAddressInputFormVisible) {
        AddressInputForm(
            viewModel = viewModel,
            onDismissRequest = { isAddressInputFormVisible = false },
            selectedAddress = editAddress,
            toggleBottomSheet = toggleBottomSheet,
            setAddressOnAppbar = setAddressOnAppbar,
        )
    }

}
