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
fun DocumentTypesScreen(navController: NavController) {
    ScreenContainer(
        title = "Документы",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Поступление товаров",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Реализация товаров",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Перемещение товаров",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Списание товаров",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Инвентаризация товаров",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}