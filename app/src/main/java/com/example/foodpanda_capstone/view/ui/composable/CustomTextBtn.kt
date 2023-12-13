package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun CustomTextBtn(name: String, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(name, color = BrandDark, style = Typography.titleSmall)
    }
}
