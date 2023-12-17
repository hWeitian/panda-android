package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun CustomTextBtn(name: String, icon: ImageVector?, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "$name button",
                    tint = BrandDark,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                name, color = BrandDark,
                style = Typography.headlineLarge
            )
        }

    }
}
