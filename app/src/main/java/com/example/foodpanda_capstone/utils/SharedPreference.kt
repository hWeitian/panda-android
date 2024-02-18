// SharedPreferencesUtils.kt

import android.content.Context
import android.content.SharedPreferences
import com.example.foodpanda_capstone.model.mock_data.AddressData
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


fun Context.getAddressList(): List<AddressData> {
    val sharedPreferences = this.sharedPreferences
    val gson = Gson()
    val json = sharedPreferences.getString(PREF_KEY_ADDRESS_LIST, null)
    val type = object : TypeToken<List<AddressData>>() {}.type
    return gson.fromJson(json, type) ?: emptyList()
}

fun Context.addAddressList(newAddress: AddressData) {
    // Retrieve the current list of addresses from SharedPreferences
    val currentAddresses = getAddressList().toMutableList()

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



