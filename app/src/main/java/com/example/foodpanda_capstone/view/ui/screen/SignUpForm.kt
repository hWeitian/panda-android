package com.example.foodpanda_capstone.view.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodpanda_capstone.R
import com.example.foodpanda_capstone.view.ui.composable.PrimaryButton
import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
import com.example.foodpanda_capstone.viewmodel.AuthViewModel
import kotlin.math.sign
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signUpForm(viewModel: AuthViewModel, navController: NavController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isButtonClicked by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isSignedUp by viewModel.signupState.collectAsState()



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Image(
            modifier = Modifier
q                .fillMaxWidth()
                .weight(1f),
            painter = painterResource(id = R.drawable.signup_profile), contentDescription = "onboard image"
        )

        Spacer(modifier = Modifier.padding(bottom = 16.dp))

        Text(text = "Let's get you started!")

        Spacer(modifier = Modifier.padding(top = 8.dp))

        Text(text = "First, let's create your foodpanda account with the details below:")

        Spacer(modifier = Modifier.padding(top = 8.dp))

        Row(modifier = Modifier
                .fillMaxWidth()
        ) {

//            OutlinedTextField(value = inputState.value,
//                onValueChange = {inputState.value = it},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 10.dp),
//                label = { Text(text = label) },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = BrandSecondary,
//                    cursorColor = BrandSecondary,
//                    focusedLabelColor = BrandSecondary
//                )
//            )
            
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = BrandSecondary,
                    focusedBorderColor = BrandSecondary,
                    focusedLabelColor = BrandSecondary,
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = BrandSecondary,
                    focusedBorderColor = BrandSecondary,
                    focusedLabelColor = BrandSecondary,
                )
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = BrandSecondary,
                focusedBorderColor = BrandSecondary,
                focusedLabelColor = BrandSecondary,
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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
            )

        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(name = "Continue", width = null) {
            isButtonClicked = true
            viewModel.signUp(firstName, lastName, email, password)
        }
        Spacer(modifier = Modifier.padding(top = 8.dp))

        LaunchedEffect(isSignedUp) {
            when (isSignedUp) {
                true -> {
                    Log.d("Signup Screen", "Navigating to Home")
                    navController.navigate("Home")
                    viewModel.onNavigationComplete()
                }
                false -> {
                    Log.d("FailedLogin", "$isSignedUp, Please try again.")
                }
                else -> Unit
            }
        }
    }
}

@Composable
fun PasswordVisibilityIcon(isPasswordVisible: Boolean, onClick: () -> Unit) {
    val icon: ImageVector = if (isPasswordVisible) {
        Icons.Default.Visibility
    } else {
        Icons.Default.VisibilityOff
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 8.dp) // Adjust padding as needed
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Toggle password visibility",
            tint = Color.Gray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun signUpFormPreview() {
    signUpForm(viewModel(),navController = rememberNavController())
}
