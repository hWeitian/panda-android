package com.example.foodpanda_capstone.model.mock_data

import com.example.foodpanda_capstone.model.FoodItem
import com.example.foodpanda_capstone.model.Playlist
import com.example.foodpanda_capstone.model.RestaurantFoodItems

val restaurantsFoodItems = listOf(
    RestaurantFoodItems(
        restaurantName = "MOS Burger",
        foodItems = listOf(
            FoodItem(
                name = "Truffle Mayo Double Beef Burger",
                description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo.",
                quantity = 1,
                price = 15.00,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Smoky BBQ Fries",
                description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger.",
                quantity = 1,
                price = 5.00,
                imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
        )
    ),
    RestaurantFoodItems(
        restaurantName = "MOS Burger 2",
        foodItems = listOf(
            FoodItem(
                name = "Truffle Mayo Double Beef Burger",
                description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo.",
                quantity = 1,
                price = 15.00,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Smoky BBQ Fries",
                description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger.",
                quantity = 1,
                price = 5.00,
                imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Smoky BBQ Fries",
                description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger.",
                quantity = 1,
                price = 5.00,
                imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
        )
    ),
    RestaurantFoodItems(
        restaurantName = "MOS Burger 3",
        foodItems = listOf(
            FoodItem(
                name = "Truffle Mayo Double Beef Burger",
                description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo.",
                quantity = 1,
                price = 15.00,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Smoky BBQ Fries",
                description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger.",
                quantity = 1,
                price = 5.00,
                imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Smoky BBQ Fries",
                description = "Smoky BBQ Fries is your new choice for flavorful french fries in MOS Burger.",
                quantity = 1,
                price = 5.00,
                imageUrl = "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?q=80&w=2970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            FoodItem(
                name = "Truffle Mayo Double Beef Burger",
                description = "Cheese slice, double cravings of beef patties topped with the exclusive Truffle Mayo.",
                quantity = 1,
                price = 15.00,
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=3099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
        )
    )
)


val publicPlaylist1 = Playlist(
    id = 1,
    name = "K-pop 1",
    imageUrl = null,
    cost = 30.00F,
    deliverDay = "Thu",
    foodItems = restaurantsFoodItems,
    isPublic = true
)


val privatePlaylist = Playlist(
    id = 1,
    name = "K-pop 1",
    imageUrl = null,
    cost = 30.00F,
    deliverDay = "Thu",
    foodItems = restaurantsFoodItems,
    isPublic = false
)

