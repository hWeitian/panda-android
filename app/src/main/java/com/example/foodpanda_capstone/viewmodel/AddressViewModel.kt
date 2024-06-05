package com.example.foodpanda_capstone.viewmodel

import addAddressList
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodpanda_capstone.model.AddressData
import com.example.foodpanda_capstone.model.AddressRepository
import com.example.foodpanda_capstone.utils.logErrorMsg
import getAddressList
import getCurrentAddress
import getStringSharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import removeAddressFromList
import updateAddress
import updateAddressSelection

class AddressViewModel(private val addressRepository: AddressRepository, private val context: Context) : ViewModel() {

//    private val _addresses = MutableStateFlow<List<AddressData>>(emptyList())
//    val addresses: StateFlow<List<AddressData>> = if (context.getAddressList().isNotEmpty()) {
//        MutableStateFlow<List<AddressData>>(context.getAddressList())
//    } else {
//        _addresses.asStateFlow()
//    }


    private val currentAddress = context.getCurrentAddress()

    private val _addresses = MutableStateFlow<List<AddressData>>(emptyList())
    val addresses: StateFlow<List<AddressData>> = _addresses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedAddress = MutableStateFlow("")
    val selectedAddress: StateFlow<String> = _selectedAddress

    private val PREFS_ADDRESS_DATA_KEY = "currentAddressPreferences"
    private val PREF_KEY_CURRENT_ADDRESS = "saved_current_address_data"
    private val PREF_KEY_CURRENT_ZIPCODE = "saved_current_zipcode"
    private val PREF_KEY_CURRENT_CITY = "saved_current_city"
    private val PREF_KEY_ADDRESS_LIST = "saved_address_list"


    init {
//        _addresses.value = addressRepository.getAddresses()
//        getInitialAddress()
        val savedAddress = context.getStringSharedPreference(PREF_KEY_CURRENT_ADDRESS)
        val savedZipCode = context.getStringSharedPreference(PREF_KEY_CURRENT_ZIPCODE)
        val savedCity = context.getStringSharedPreference(PREF_KEY_CURRENT_CITY)

        _selectedAddress.value = if (savedAddress != "" && savedZipCode != "" && savedCity != "") {
            "$savedAddress $savedZipCode $savedCity"
        } else {
            "Add your address here"
        }

    }

    fun getInitialAddress() {
        val updatedAddress = context.getAddressList(currentAddress)
        _addresses.value = createNewAddressList(updatedAddress)
    }

    fun deleteAll() {
        _addresses.value = emptyList()
    }
    private fun createNewAddressList(data: List<AddressData>): List<AddressData> {
        val final = mutableListOf<AddressData>()

        data.forEach { addressData ->
            final.add(
                AddressData(
                    address = addressData.address,
                    city = addressData.city,
                    zipCode = addressData.zipCode,
                    isSelected = addressData.isSelected
                )
            )
        }
        println("Final Address Data: $final")
        return final.toList()
    }

    fun selectAddress(selectedAddress: AddressData) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.updateAddressSelection(selectedAddress)
//                _addresses.value = context.getAddressList(currentAddress)

            } catch (e: Exception) {
                logErrorMsg("selectAddress", e)
            }
        }
    }

    fun saveAddress(address: AddressData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.addAddressList(address)
                _addresses.value = context.getAddressList(currentAddress)

            } catch (e: Exception) {
                logErrorMsg("saveAddress", e)
            }
        }
    }

    fun removeAddress(address: AddressData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.removeAddressFromList(address)
                _addresses.value = context.getAddressList(currentAddress)

            } catch (e: Exception) {
                logErrorMsg("removeAddress", e)
            }
        }
    }

    fun updateAddress(currentAddress: AddressData, editedAddress: AddressData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                context.updateAddress(addressToUpdate = currentAddress, newAddressData = editedAddress)
                _addresses.value = context.getAddressList(currentAddress)

            } catch (e: Exception) {
                logErrorMsg("updateAddress", e)
            }
        }
    }

    fun setAddressOnAppbar (
        address: String? = null,
        city: String? = null,
        zipCode: String? = null
    ) {
        if (address == null && city == null && zipCode == null) {
            _selectedAddress.value = "Add your address here"
        } else {
            _selectedAddress.value = "$address, $city, $zipCode"
        }
    }


    private fun refreshAddresses() {
        _addresses.value = context.getAddressList(currentAddress)
    }


}
