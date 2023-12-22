package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.R

@Composable
fun ScreenBottomSpacer() {
    Spacer(modifier = Modifier.size(dimensionResource(R.dimen.page_bottom_padding)))
}
