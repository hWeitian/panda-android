package com.example.foodpanda_capstone.view.ui.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun Modal(
    title: String,
    description: String,
    buttonTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
                    ) {
                        Text(text = title, style = Typography.titleSmall)
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close button",
                            tint = BrandDark,
                            modifier = Modifier.size(20.dp).clickable { onDismissRequest() }
                        )
                    }
                    Text(
                        text = description,
                        style = Typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))
                PrimaryButton(name = buttonTitle, width = null) { onConfirmation() }
            }

        }
    }
}
