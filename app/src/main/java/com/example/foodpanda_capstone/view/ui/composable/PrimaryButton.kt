package com.example.foodpanda_capstone.view.ui.composable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun PrimaryButton(name: String, width: Int?, btnClick: () -> Unit) {
    Button(

        modifier = if(width == null) Modifier.fillMaxWidth() else Modifier.width(width.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = BrandDark),
        onClick = { btnClick() }
    ) {
        Text(text = name, style = Typography.titleSmall)
    }
}
