package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun SectionTitleAndBtn(title: String, btnTitle: String?, icon: ImageVector?, btnClicked: () -> Unit?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Row(
            Modifier.width(200.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = "icon",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(20.dp),
                    tint = BrandDark
                )
            }
            Text(text = title, style = Typography.titleSmall)
        }
        if(btnTitle != null && btnClicked != null) {
            CustomTextBtn(btnTitle, null, null) { btnClicked() }
        }
    }
}
