package com.tamersarioglu.transparentbottombar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tamersarioglu.transparentbottombar.presentation.screens.HomeScreen
import com.tamersarioglu.transparentbottombar.presentation.screens.SearchScreen
import com.tamersarioglu.transparentbottombar.presentation.screens.ProfileScreen
import com.tamersarioglu.transparentbottombar.presentation.screens.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = modifier
    ) {
        composable(NavRoutes.HOME) { HomeScreen() }
        composable(NavRoutes.SEARCH) { SearchScreen() }
        composable(NavRoutes.PROFILE) { ProfileScreen() }
        composable(NavRoutes.SETTINGS) { SettingsScreen() }
    }
} 