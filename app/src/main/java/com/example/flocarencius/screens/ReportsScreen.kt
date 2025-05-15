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
fun ReportsScreen(navController: NavController) {
    ScreenContainer(
        title = "Отчеты",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Остатки товаров",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Продажи",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Закупки",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Движения товаров",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}