package com.example.foodpanda_capstone.model

data class AddressData (
    val address: String,
    val city: String,
    val zipCode: String,
    var isSelected: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // Check if both objects are the same instance
        if (other !is AddressData) return false

        return address == other.address &&
                city == other.city &&
                zipCode == other.zipCode

    }
}


