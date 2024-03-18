package com.example.foodpanda_capstone.view.ui.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.Typography
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

@Composable
//fun onBoardingScreen(onSignedIn: () -> Unit) {
fun onBoardingScreen(navController: NavController) {

    var isButtonClicked by remember { mutableStateOf(false) }

//    val signInLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        val response = IdpResponse.fromResultIntent(result.data)
//        if (result.resultCode == Activity.RESULT_OK) {
//            // Successfully signed in
//            onSignedIn()
//        } else {
//            // Handle sign-in failure
//            response?.error?.let {
//                // Handle error
//            }
//        }
//    }


    Column(modifier = Modifier.background(BrandPrimary).fillMaxWidth()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f),
            painter = painterResource(id = R.drawable.panda_onboard_pic), contentDescription = "onboard image"
        )

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Adjust padding as needed
            ) {
                Text(style = Typography.titleLarge,text = "Sign up or Log in")

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(text = "Select your preferred method to continue", style = Typography.bodyLarge)

                Spacer(modifier = Modifier.weight(1f))


//                ElevatedButton(
//                    // FirebaseUI sign-in button
//                    onClick = {
//                        val providers = arrayListOf(
//                            AuthUI.IdpConfig.EmailBuilder().build(),
//                            AuthUI.IdpConfig.GoogleBuilder().build()
//                            // Add more providers as needed
//                        )
//
//                        signInLauncher.launch(
//                            AuthUI.getInstance()
//                                .createSignInIntentBuilder()
//                                .setAvailableProviders(providers)
//                                .build()
//                        )
//
//                    }
//                ) {
//                    Text(text = "Sign in to your account")
//                }

                PrimaryButton(name = "Login to your account", width = null ) {
                    isButtonClicked = true
                    navController.navigate("Login Form")

                }

                Spacer(modifier = Modifier.padding(bottom = 16.dp))

                PrimaryButton(name = "Sign up for an account", width = null ) {
                    isButtonClicked = true
                    navController.navigate("Signup Form")

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun onBoardingScreenPreview() {
//    onBoardingScreen(onSignedIn = {})
    onBoardingScreen(navController = rememberNavController())

}
