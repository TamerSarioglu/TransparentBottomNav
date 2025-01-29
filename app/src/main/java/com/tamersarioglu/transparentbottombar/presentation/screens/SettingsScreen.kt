package com.tamersarioglu.transparentbottombar.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        settingsItems.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                ListItem(
                    headlineContent = { Text(item) },
                    trailingContent = { Switch(checked = false, onCheckedChange = {}) }
                )
            }
        }
    }
}

private val settingsItems = listOf(
    "Dark Mode",
    "Notifications",
    "Privacy",
    "Sound",
    "Vibration",
    "Auto-update",
    "Backup"
) 