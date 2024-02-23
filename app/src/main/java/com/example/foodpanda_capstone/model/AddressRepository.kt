package com.example.foodpanda_capstone.model

import com.example.foodpanda_capstone.model.mock_data.AddressData

class AddressRepository {
    private val _addresses = mutableListOf<AddressData>()

    fun saveAddress(address: AddressData) {
        _addresses.add(address)
    }

    fun removeAddress(address: AddressData) {
        _addresses.remove(address)
    }

    fun getAddresses(): List<AddressData> {
        return _addresses.toList()
    }


    fun getAddressIndex(address: String, city: String, zipCode: String): Int {
        val index = _addresses.indexOfFirst { it.address == address && it.city == city && it.zipCode == zipCode }
        println("Found address index: $index")
        return index
    }




    fun updateAddressUsingIndex(addressIndex: Int, updatedAddress: AddressData) {
        print(updatedAddress)
        if (addressIndex != -1 && addressIndex < _addresses.size) {
            _addresses[addressIndex] = updatedAddress
        }
    }


}
