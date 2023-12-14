package com.example.foodpanda_capstone.view.ui.composable
//
//import android.app.Activity
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.toArgb
//import androidx.compose.ui.platform.LocalView
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.core.view.WindowCompat
//import com.example.foodpanda_capstone.R
//import com.example.foodpanda_capstone.view.ui.theme.BrandSecondary
//import com.example.foodpanda_capstone.view.ui.theme.Typography
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InnerTopAppBar(title: String, backBtnClick: () -> Unit, content: @Composable () -> Unit) {
//
//    // Change status bar background colour
//    val activity = LocalView.current.context as Activity
//    val backgroundArgb = Color.White.toArgb()
//    activity.window.statusBarColor = backgroundArgb
//
//    // Change status bar icons and text colour
//    val wic = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
//    wic.isAppearanceLightStatusBars = true // true or false depending on background colour
//
//    Scaffold(
//        topBar = {
//            Surface(modifier = Modifier.drawBehind {
//                drawLine(
//                    color = BrandSecondary,
//                    start = Offset(0f, size.height),
//                    end = Offset(size.width, size.height),
//                    strokeWidth = 2f,
//                )
//            }) {
//                TopAppBar(
//                    colors = TopAppBarDefaults.smallTopAppBarColors(
//                        containerColor = Color.White,
//                        titleContentColor = Color.Black,
//                    ),
//                    title = {
//                        Text(title, style = Typography.titleMedium)
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { backBtnClick() }) {
//                            Image(
//                                painter = painterResource(id = R.drawable.ic_arrow_tail_back),
//                                contentDescription = "Back Button",
//                                modifier = Modifier.size(25.dp)
//                            )
//                        }
//                    }
//                )
//            }
//
//        },
//        modifier = Modifier.fillMaxSize()
//    ) { innerPadding ->
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(bottom = 25.dp, start = 15.dp, end = 15.dp)
//        )
//        {
//            content()
//        }
//
//    }
//
//}
