package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.theme.BrandDark
import com.example.foodpanda_capstone.view.ui.theme.InteractionPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun CustomTextBtn(name: String, iconVector: ImageVector?, iconImgId: Int?, onClick: () -> Unit) {
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
            if (iconImgId != null) {
                Image(
                    painter = painterResource(id = iconImgId),
                    contentDescription = "@name button",
                    modifier = Modifier
                        .width(25.dp)
                        .background(InteractionPrimary, CircleShape)
                        .clip(CircleShape)
                        .padding(5.dp, 5.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }
            if (iconVector != null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = "$name button",
                    tint = BrandDark,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                name, color = BrandDark,
                style = if (iconVector != null || iconImgId != null) Typography.titleSmall
                else Typography.headlineLarge
            )
        }

    }
}
