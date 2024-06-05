package com.example.foodpanda_capstone.view.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.theme.Typography

@Composable
fun ErrorScreen(
    errorTitle: String? = null,
    description: String? = null,
    buttonTitle: String = "",
    onButtonClick: (() -> Unit)? = null,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation_pink))

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(250.dp)
            )
            if (errorTitle != null) {
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = errorTitle, style = Typography.titleMedium)
            }
            if (description != null) {
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = description, style = Typography.bodyLarge)
                Spacer(modifier = Modifier.size(50.dp))
            }
            if (onButtonClick != null) {
                PrimaryButton(name = buttonTitle, width = 300) {
                    onButtonClick()
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        errorTitle = "No playlist found",
        description = "Please try to amend your input",
        buttonTitle = "Back",
        onButtonClick = {}
    )
}
