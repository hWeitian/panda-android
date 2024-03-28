// SharedPreferencesUtils.kt

import android.content.Context
import android.content.SharedPreferences
import com.example.foodpanda_capstone.model.AddressData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

const val PREFS_ADDRESS_DATA_KEY = "currentAddressPreferences"
const val PREF_KEY_CURRENT_ADDRESS = "saved_current_address_data"
const val PREF_KEY_CURRENT_ZIPCODE = "saved_current_zipcode"
const val PREF_KEY_CURRENT_CITY = "saved_current_city"
const val PREF_KEY_ADDRESS_LIST = "saved_address_list"

val Context.sharedPreferences: SharedPreferences
    get() = getSharedPreferences(PREFS_ADDRESS_DATA_KEY, Context.MODE_PRIVATE)

fun Context.getStringSharedPreference(preferenceKey: String): String {
    val sharedPreferences = this.sharedPreferences
    return sharedPreferences.getString(preferenceKey, null) ?: ""
}

fun Context.getCurrentAddress(): AddressData {
    val address = getStringSharedPreference(PREF_KEY_CURRENT_ADDRESS)
    val zipCode = getStringSharedPreference(PREF_KEY_CURRENT_ZIPCODE)
    val city = getStringSharedPreference(PREF_KEY_CURRENT_CITY)

    return AddressData(address, city, zipCode, true)
}

fun Context.setStringSharedPreference(preferenceKey: String, addressDetails: String) {
    val sharedPreferences = this.sharedPreferences
    val editor = sharedPreferences.edit()
    editor.putString(preferenceKey, addressDetails)
    editor.apply()
}

fun Context.setSharedPreferenceAddressData(address: String, zipCode: String, city: String) {
    val editor = this.sharedPreferences.edit()
    editor.putString(PREF_KEY_CURRENT_ADDRESS, address)
    editor.putString(PREF_KEY_CURRENT_ZIPCODE, zipCode)
    editor.putString(PREF_KEY_CURRENT_CITY, city)
    editor.apply()
}


//fun Context.getAddressList(): List<AddressData> {
//    val sharedPreferences = this.sharedPreferences
//    val gson = Gson()
//    val json = sharedPreferences.getString(PREF_KEY_ADDRESS_LIST, null)
//    val type = object : TypeToken<List<AddressData>>() {}.type
//    return gson.fromJson(json, type) ?: emptyList()
//}

fun Context.getAddressList(selectedAddress: AddressData): List<AddressData> {
    val sharedPreferences = this.sharedPreferences
    val gson = Gson()
    val json = sharedPreferences.getString(PREF_KEY_ADDRESS_LIST, null)
    val type = object : TypeToken<List<AddressData>>() {}.type
    val addressList: List<AddressData> = gson.fromJson(json, type) ?: emptyList()
    val currentAddress = getCurrentAddress()

    // Reset the 'isSelected' property for the entire list
//    addressList.forEach { it.isSelected = false }

    // Find the selected address in the list and mark it as selected
    addressList.map { address ->
        if (address.address == currentAddress.address && address.city == currentAddress.city && address.zipCode == currentAddress.zipCode){
//            println("true")
            address.isSelected = true
//            address.copy(isSelected = true)
        } else {
//            println("false")
            address.isSelected = false
//            address.copy(isSelected = false)
        }
    }
    return addressList
}

fun Context.addAddressList(newAddress: AddressData) {
    // Retrieve the current list of addresses from SharedPreferences
    val currentAddresses = getAddressList(getCurrentAddress()).toMutableList()

    // Add the new address to the list
    currentAddresses.add(newAddress)

    // Serialize the updated list to JSON
    val gson = Gson()
    val json = gson.toJson(currentAddresses)

    // Save the JSON string to SharedPreferences
    val editor = this.sharedPreferences.edit()
    editor.putString(PREF_KEY_ADDRESS_LIST, json)
    editor.apply()
}

fun Context.removeAddressFromList(addressToRemove: AddressData) {
    // Retrieve the current list of addresses from SharedPreferences
    val currentAddresses: MutableList<AddressData> = getAddressList(getCurrentAddress()).toMutableList()

    // Remove the desired address from the list
    var removeSucceeded: Boolean =  currentAddresses.remove(addressToRemove)
    print(removeSucceeded)

    // Serialize the updated list to JSON
    val gson = Gson()
    val json = gson.toJson(currentAddresses)

    // Save the JSON string to SharedPreferences
    val editor = this.sharedPreferences.edit()
    editor.putString(PREF_KEY_ADDRESS_LIST, json)
    editor.apply()
}



fun Context.updateAddress(addressToUpdate: AddressData, newAddressData: AddressData) {
    // Retrieve the current list of addresses from SharedPreferences
    val currentAddresses = getAddressList(getCurrentAddress()).toMutableList()

    // Get the current index of the address to update
    val updateIndex = currentAddresses.indexOf(addressToUpdate)

    // Check if address exists
    if (updateIndex != -1) {
        // Replace the old address with the new one
        currentAddresses[updateIndex] = newAddressData

        // Serialize the updated list to JSON
        val gson = Gson()
        val json = gson.toJson(currentAddresses)

        // Save the JSON string to SharedPreferences
        val editor = this.sharedPreferences.edit()
        editor.putString(PREF_KEY_ADDRESS_LIST, json)
        editor.apply()
    } else {
        print("Address not found in the list.")
    }
}

fun Context.updateAddressSelection(selectedAddress: AddressData) {
    println("here")
    // Retrieve the current list of addresses from SharedPreferences
//    val currentAddresses: MutableList<AddressData> = getAddressList(getCurrentAddress()).toMutableList()

    // Loop through each address
//    for (addressData in currentAddresses) {
//        // If the current address matches the selected one, set isSelected to true, otherwise set to false
//        if(addressData == selectedAddress) {
//            addressData.isSelected = true
//        } else {
//            addressData.isSelected = false
//        }
//    }

    // Set the currentAddress in SharedPreferences
    setSharedPreferenceAddressData(selectedAddress.address, selectedAddress.zipCode, selectedAddress.city)

    // Serialize the updated list to JSON
//    val gson = Gson()
//    val json = gson.toJson(currentAddresses)
//
//    // Save the JSON string to SharedPreferences
//    val editor = this.sharedPreferences.edit()
//    editor.putString(PREF_KEY_ADDRESS_LIST, json)
//    editor.apply()
}

