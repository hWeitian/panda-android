package com.example.foodpanda_capstone.view.ui.composable


import androidx.compose.foundation.layout.PaddingValues
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
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun PrimaryButton(name: String, width: Int?, isEnabled: Boolean = true, btnClick: () -> Unit) {
    Button(
        modifier = if (width == null) Modifier.fillMaxWidth() else Modifier.width(width.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(15.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = InteractionPrimary),
        onClick = { btnClick() },
        enabled = isEnabled
    ) {
        Text(text = name, style = Typography.titleSmall)
    }
}
