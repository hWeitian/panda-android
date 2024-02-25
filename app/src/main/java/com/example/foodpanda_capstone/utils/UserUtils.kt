package com.example.foodpanda_capstone.utils

public class UserUtils {
        companion object {
        private var userUID: String? = null
        fun getUserUID(): String? {
            return userUID
        }

        fun setUserUID(uid: String) {
            userUID = uid
        }
    }

}
