package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.view.ui.theme.InteractionSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputField (label: String, inputState: MutableState<String>) {

    OutlinedTextField(value = inputState.value,
        onValueChange = {inputState.value = it},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandSecondary,
            cursorColor = BrandSecondary,
            focusedLabelColor = BrandSecondary
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumberInputField (label: String, inputState: MutableState<String>) {

    OutlinedTextField(value = inputState.value,
        onValueChange = {inputState.value = it},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandSecondary,
            cursorColor = BrandSecondary,
            focusedLabelColor = BrandSecondary
        )
    )
}
