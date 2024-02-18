package com.example.foodpanda_capstone.viewmodel

import addAddressList
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.foodpanda_capstone.model.AddressRepository
import com.example.foodpanda_capstone.model.mock_data.AddressData
import getAddressList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddressViewModel(private val addressRepository: AddressRepository, private val context: Context) : ViewModel() {

    private val _addresses = MutableStateFlow<List<AddressData>>(emptyList())
    val addresses: StateFlow<List<AddressData>> = if (context.getAddressList().isNotEmpty()) {
        MutableStateFlow<List<AddressData>>(context.getAddressList())
    } else {
        _addresses.asStateFlow()
    }


    init {
        _addresses.value = addressRepository.getAddresses()
    }

    fun saveAddress(address: AddressData) {

        addressRepository.saveAddress(address)
        context.addAddressList(address)
        refreshAddresses()
    }

    fun getAddressIndex(address: String, city: String, zipCode: String): Int {
        return addressRepository.getAddressIndex(address,city,zipCode)
//        refreshAddresses()
    }

    fun updateAddress(addressIndex: Int , editedAddress: AddressData) {
        addressRepository.updateAddressUsingIndex(addressIndex, editedAddress)
        refreshAddresses()
    }



    private fun refreshAddresses() {
        _addresses.value = addressRepository.getAddresses()
    }



}
