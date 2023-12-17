package com.example.foodpanda_capstone.model.mock_data

import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist

val playlistItems = listOf(
    FoodItem(
        name = "Truffle Mayo Double Beef Burger",
        description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo, fresh coral green, another layer of the creamy Truffle Mayo.",
        quantity = 1,
        price = 15.00,
        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    FoodItem(
        name = "Smoky BBQ Fries",
        description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger now and Teriyaki Mayo Fries will still be here for you!",
        quantity = 1,
        price = 5.00,
        imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    FoodItem(
        name = "Truffle Mayo Double Beef Burger",
        description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo, fresh coral green, another layer of the creamy Truffle Mayo.",
        quantity = 1,
        price = 15.00,
        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    FoodItem(
        name = "Smoky BBQ Fries",
        description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger now and Teriyaki Mayo Fries will still be here for you!",
        quantity = 1,
        price = 5.00,
        imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    FoodItem(
        name = "Truffle Mayo Double Beef Burger",
        description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo, fresh coral green, another layer of the creamy Truffle Mayo.",
        quantity = 1,
        price = 15.00,
        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    FoodItem(
        name = "Smoky BBQ Fries",
        description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger now and Teriyaki Mayo Fries will still be here for you!",
        quantity = 1,
        price = 5.00,
        imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    )
)

val playlist = Playlist(
    id = 1,
    name = "K-pop 1",
    imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=3160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    cost = 30.00F,
    deliverDay = "Thu",
    foodItems = playlistItems
)
