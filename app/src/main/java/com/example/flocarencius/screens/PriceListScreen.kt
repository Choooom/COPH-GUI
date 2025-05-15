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
fun PriceListScreen(navController: NavController) {
    ScreenContainer(
        title = "Прайс-лист",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Создать",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Редактировать",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Удалить",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Поиск",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}