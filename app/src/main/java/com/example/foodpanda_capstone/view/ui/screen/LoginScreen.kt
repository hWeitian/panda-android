package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import androidx.navigation.NavController
import com.example.foodpanda_capstone.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(viewModel: AuthViewModel, navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var loginResultText by remember { mutableStateOf<String?>(null) }
    val loginState by viewModel.loginState.collectAsState()



//    Log.d("LoginScreen", "Composable recomposed. navigateToHome: $navigateToHome, loginState: $loginState")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, top = 150.dp)
    ) {
        Text(
            text = "Login with your email",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = BrandSecondary,
                focusedBorderColor = BrandSecondary,
                focusedLabelColor = BrandSecondary,
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = BrandSecondary,
                focusedBorderColor = BrandSecondary,
                focusedLabelColor = BrandSecondary,
            ),
            trailingIcon = {
                PasswordVisibilityIcon(
                    isPasswordVisible = isPasswordVisible) {
                    isPasswordVisible = !isPasswordVisible
                }
            },
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.padding(4.dp))


        Column(
            modifier = Modifier.weight(1f),
        ) {
            PrimaryButton(name = "Login", width = null) {
                isButtonClicked = true
                viewModel.signIn(email, password) }


            Spacer(modifier = Modifier.padding(8.dp))

            loginResultText?.let {
                Text(it, color = if (it.contains("Success")) Color.Green else Color.Red)
            }

            when {
                loginState && isButtonClicked -> Text("Login Successful", color = Color.Green)
                !loginState && isButtonClicked -> Text("Login Failed", color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Navigation logic based on login state
            LaunchedEffect(loginState) {
                when (loginState) {
                    true -> {
                        Log.d("LoginScreen", "Navigating to Home")
                        navController.navigate("Home")
                        viewModel.onNavigationComplete()
                    }
                    false -> {
                        Log.d("FailedLogin", "$loginState, Please try again.")
                    }
                    else -> Unit
                }
            }
        }
    }
}

private fun loginWithEmailPassword(email: String, password: String, navController: NavController, onLoginResult: (String) -> Unit) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            val resultText = if (task.isSuccessful) {
                // Login successful
                navController.navigate("Home")
                "Login Success"
            } else {
                // Login failed
                Log.e("LoginScreen", "Login failed: ${task.exception?.message}")
                "Login Failed: ${task.exception?.message}"
            }
            onLoginResult(resultText)
        }
}
private fun loginWithEmailPassword2(email: String, password: String, navController: NavController, onLoginResult: (String) -> Unit) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
                Log.d("LoginScreen", "signInWithEmail:success")
                val user = FirebaseAuth.getInstance().currentUser
                navController.navigate("Home")
                onLoginResult("Login Success")
            } else {
                // If sign in fails, display a message to the user.
                Log.w("LoginScreen", "signInWithEmail:failure", task.exception)
                onLoginResult("Login Failed: ${task.exception?.message}")
            }
        }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
}
