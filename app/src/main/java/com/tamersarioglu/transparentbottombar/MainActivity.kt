package com.tamersarioglu.transparentbottombar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.tamersarioglu.transparentbottombar.presentation.components.TransparentBottomBar
import com.tamersarioglu.transparentbottombar.presentation.navigation.AppNavigation
import com.tamersarioglu.transparentbottombar.ui.theme.TransparentBottomBarTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TransparentBottomBarTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val hazeState = remember { HazeState() }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { 
            TransparentBottomBar(
                navController = navController,
                hazeState = hazeState
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .haze(
                    hazeState,
                    HazeStyle(
                        tint = Color.Black.copy(alpha = .4f),
                        blurRadius = 40.dp,
                        noiseFactor = 0.1f
                    )
                )
        ) {
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}