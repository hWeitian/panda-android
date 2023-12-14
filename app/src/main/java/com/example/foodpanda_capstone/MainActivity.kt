package com.example.foodpanda_capstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodpanda_capstone.view.ui.screen.PlaylistFormScreen
import com.example.foodpanda_capstone.view.ui.screen.PlaylistScreen
import com.example.foodpanda_capstone.view.ui.theme.BrandPrimary
import com.example.foodpanda_capstone.view.ui.theme.FoodpandaCapstoneTheme
import com.example.foodpanda_capstone.view.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FoodpandaCapstoneTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        PlaylistScreen(navController)
                    }
                    composable("playlist-form") {
                        PlaylistFormScreen(navController)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Playlist Title Large", color = Color.Black, style = Typography.titleLarge)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Title Medium", color = Color.Black, style = Typography.titleMedium)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Title Small", color = Color.Black, style = Typography.titleSmall)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Headline Large", color = Color.Black, style = Typography.headlineLarge)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Headline Medium", color = Color.Black, style = Typography.headlineMedium)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Headline Small", color = Color.Black, style = Typography.headlineSmall)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Body Large", color = Color.Black, style = Typography.bodyLarge)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Body Medium", color = Color.Black, style = Typography.bodyMedium)
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Playlist Body Small", color = Color.Black, style = Typography.bodySmall)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodpandaCapstoneTheme {
        Greeting("Android")
    }
}
