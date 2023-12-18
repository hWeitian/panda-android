package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun CustomOutlinedBtn(name: String, width: Int?, btnClick: () -> Unit) {
    OutlinedButton(
        modifier = if (width == null) Modifier.fillMaxWidth() else Modifier.width(width.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(15.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = InteractionPrimary),
        border = BorderStroke(1.dp, InteractionPrimary),
        onClick = { btnClick() }
    )
    {
        Text(text = name, style = Typography.titleSmall)
    }
}
