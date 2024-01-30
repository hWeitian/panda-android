package com.example.foodpanda_capstone.utils

import com.google.firebase.auth.FirebaseAuth

object FirebaseUtils {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}
