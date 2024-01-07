package com.example.foodpanda_capstone.viewmodel

import android.util.Log

fun logErrorMsg(functionName: String, error: Exception){
    Log.e("PdError", "Error at $functionName - $error")
}
