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
fun WorkScreen(navController: NavController) {
    ScreenContainer(
        title = "Работа",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Создать документ",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Редактировать документ",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Удалить документ",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Поиск документа",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}