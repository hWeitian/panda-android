package com.example.foodpanda_capstone.view.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.model.mock_data.AddressData
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
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
    setAddressOnAppbar: (String, String, String) -> Unit,
    onAddressSelected: String?
) {
    if (isVisible) {
        val addresses by addressViewModel.addresses.collectAsState()
        var selectedAddress by remember { mutableStateOf<String?>(null) }
        var isAddressInputFormVisible by remember { mutableStateOf(false) }
        var editedAddress by remember { mutableStateOf<AddressData?>(null) } // Track edited address

        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = {toggleBottomSheet()},
            sheetState = sheetState) {
                // Define your input fields and buttons inside the BottomSheet
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    LazyColumn {
                        items(addresses) { address ->
                            RoundCheckBoxWithText(
                                index = addresses.indexOf(address), // Pass the index here
                                address = address.address,
                                city = address.city,
                                zipCode = address.zipCode,
                                selectedAddress = selectedAddress,
                                editedAddress = editedAddress,
                                viewModel = addressViewModel,
                                toggleBottomSheet = toggleBottomSheet,
                                setAddressOnAppbar = setAddressOnAppbar,
                            ) { selectedAddress = it
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Clickable text with an icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isAddressInputFormVisible = true
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add address",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Add your address here")
                    }
                }
                if (isAddressInputFormVisible) {
                    AddressInputForm(
                        viewModel = addressViewModel,
                        onDismissRequest = { isAddressInputFormVisible = false },
                        editedAddress = editedAddress,
                        setAddressOnAppbar = setAddressOnAppbar,
                        toggleBottomSheet = toggleBottomSheet,)
                }

                // Animated visibility for the address form
//                AnimatedVisibility(
//                    visible = isAddressInputFormVisible,
//                    enter = slideInVertically(initialOffsetY = { it }),
//                    exit = slideOutVertically(targetOffsetY = { it })
//                ) {
//                    AddressInputForm(
//                        viewModel = addressViewModel,
//                        onDismissRequest = { isAddressInputFormVisible = false },
//                        editedAddress = editedAddress
//                    )
//                }
            }
//            sheetPeekHeight = 300.dp,
//            containerColor = Color.White
//
//        {
//            // Your main content here
//            // For example, display the saved address
////        val savedAddress = addressViewModel.addresses
////        Text("Saved Address: $savedAddress")
//        }
    }
}

@Composable
fun AddressInputForm(
    viewModel: AddressViewModel,
    onDismissRequest: () -> Unit,
    editedAddress: AddressData?,
    toggleBottomSheet: () -> Unit,
    setAddressOnAppbar: (String,String,String) -> Unit,
) {
    var address by remember { mutableStateOf(editedAddress?.address ?: "") }
    var city by remember { mutableStateOf(editedAddress?.city ?: "") }
    var zipCode by remember { mutableStateOf(editedAddress?.zipCode ?: "") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            // Title of the AlertDialog
            Text("Add Address")
        },
        text = {
            // Content of the AlertDialog
            Column {
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Street") })
                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
                OutlinedTextField(value = zipCode, onValueChange = { zipCode = it }, label = { Text("Zip Code") })
            }
        },
        confirmButton = {
            // Confirm button in the AlertDialog
            Row() {
                Button(
                    onClick = {
                        //address from input which has yet to be saved. If the address from database is not null, update old with new address.
                        //Else just save into database with the new input address.
                        val currentAddress = AddressData(address, city, zipCode)
                        if (editedAddress != null) {
                            // address from current database
                            val addressIndex = viewModel.getAddressIndex(editedAddress.address, editedAddress.city, editedAddress.zipCode)
                            viewModel.updateAddress( addressIndex,currentAddress!!)
                        } else {
                            viewModel.saveAddress(currentAddress)
                        }

                        onDismissRequest()
                    }
                ) {
                    Text("Add Address")
                }

                Button(
                    onClick = {
                        //address from input which has yet to be saved. If the address from database is not null, update old with new address.
                        //Else just save into database with the new input address.
                        viewModel.removeAddress(AddressData(address, city, zipCode))

                        onDismissRequest()
                    }
                ) {
                    Text("Remove Address")
                }
            }

        }
    )
}

@Composable
fun RoundCheckBoxWithText(
    index: Int, // Added index parameter
    address: String,
    city: String,
    zipCode: String,
    selectedAddress: String?,
    viewModel: AddressViewModel,
    editedAddress: AddressData?,
    toggleBottomSheet: () -> Unit, // Added toggleBottomSheet paramet
    setAddressOnAppbar: (String, String, String) -> Unit,
    onAddressSelected: (String) -> Unit,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val isChecked = selectedAddress == address
        var isAddressInputFormVisible by remember { mutableStateOf(false) }
        var editAddress by remember { mutableStateOf(editedAddress) }
        var currentAddress by remember { mutableStateOf(selectedAddress) }

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable {
                    onAddressSelected(address)
                    currentAddress = address
                }
                .background(Color.White)
                .border(2.dp, BrandPrimary, shape = CircleShape)
        ) {
            if (isChecked) {
                Icon(
                    imageVector = Icons.Default.RadioButtonChecked,
                    contentDescription = "Checked",
                    tint = BrandPrimary,
                    modifier = Modifier
                        .size(24.dp)
                )
                toggleBottomSheet()
                setAddressOnAppbar(address, city, zipCode)
                context.setSharedPreferenceAddressData(address, city,zipCode)


            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Address: $address",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "City: $city, Zip Code: $zipCode",
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Address",
            modifier = Modifier
                .fillMaxWidth()
                .size(24.dp)
                .clickable {
                    editAddress = AddressData(address, city, zipCode)
                    isAddressInputFormVisible = true
                }
        )
        if (isAddressInputFormVisible) {
            AddressInputForm(
                viewModel = viewModel,
                onDismissRequest = { isAddressInputFormVisible = false },
                editedAddress = editAddress,
                toggleBottomSheet= toggleBottomSheet,
                setAddressOnAppbar = setAddressOnAppbar,
            )
        }
    }
}

//@Preview
//@Composable
//fun AddressFormScreenPreview() {
//    val viewModel = AddressViewModel(AddressRepository())
////    AddressFormScreen(navController(),viewModel)
//}