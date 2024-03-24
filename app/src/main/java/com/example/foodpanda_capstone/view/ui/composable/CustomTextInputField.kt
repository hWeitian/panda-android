package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputField(
    label: String,
    inputValue: String,
    updateInput: (input: String) -> Unit,
    icon: ImageVector? = null
) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = { updateInput(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandSecondary,
            cursorColor = BrandSecondary,
            focusedLabelColor = BrandSecondary
        ),
        singleLine = true,
        leadingIcon = {
            icon?.let {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .size(18.dp),
                            imageVector = icon,
                            contentDescription = "leading icon",
                            tint = BrandDark,
                        )
                        Canvas(
                            modifier = Modifier.height(24.dp)
                        ) {
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 2.0F
                            )
                        }
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumberInputField(
    label: String,
    inputValue: String,
    updateInput: (input: String) -> Unit,
    icon: ImageVector? = null
) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = { updateInput(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BrandSecondary,
            cursorColor = BrandSecondary,
            focusedLabelColor = BrandSecondary
        ),
        singleLine = true,
        leadingIcon = {
            icon?.let {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp)
                                .size(18.dp),
                            imageVector = icon,
                            contentDescription = "leading icon",
                            tint = BrandDark,
                        )
                        Canvas(
                            modifier = Modifier.height(24.dp)
                        ) {
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 2.0F
                            )
                        }
                    }
                )
            }
        }
    )
}
