package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.flocarencius.components.MenuButton
import com.example.flocarencius.components.ScreenContainer

@Composable
fun SettingsScreen(navController: NavController) {
    ScreenContainer(
        title = "Настройки",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Пользователь",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Организация",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "База данных",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Интерфейс",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Синхронизация",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}