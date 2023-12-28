package com.example.foodpanda_capstone.view.ui.screen
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.viewmodel.LoginFormViewModel
import androidx.navigation.NavController
import com.example.foodpanda_capstone.model.LoginForm


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun LoginScreen(loginFormViewModel: LoginFormViewModel, navController: NavController) {

fun LoginScreen(loginFormViewModel: LoginFormViewModel, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }

    //observes the navigateToHome state from the LoginFormViewModel.
    //When it detects that navigation is required, it triggers the navigation using the NavController.
    val navigateToHome by loginFormViewModel.navigateToHome.collectAsState()
    val loginState by loginFormViewModel.loginState.collectAsState()

    Log.d("LoginScreen", "Composable recomposed. navigateToHome: $navigateToHome, loginState: $loginState")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp, top = 100.dp)
    ) {
        Text(text = "Login with your email",
            style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(text = "I forgot my password",
            style = MaterialTheme.typography.bodyMedium)


        Column(modifier = Modifier
            .padding(top = 300.dp)) {
             Button(modifier = Modifier
                 .padding(8.dp), onClick = {
                 isButtonClicked = true
                 // Trigger login when the button is pressed
                 loginFormViewModel.login(email, password)
                 Log.d("LoginScreen", "Login button clicked")}

                  )
             {
                 Text(text = "Login")

             }

            LaunchedEffect(navigateToHome) {
                if (navigateToHome) {
                    Log.d("LoginScreen", "Navigating to Home")
                    navController.navigate("Home")
                    loginFormViewModel.onNavigationComplete()
                    Log.d("LoginScreen", "Navigating to Login Form")

                }
            } 


            Spacer(modifier = Modifier.padding(8.dp))

            when {

                loginState && isButtonClicked -> Text("Login Successful", color = Color.Green)
                !loginState && isButtonClicked -> Text("Login Failed", color = Color.Red)
            }

            PrimaryButton(name = "Cancel", width = null) {


            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
}
