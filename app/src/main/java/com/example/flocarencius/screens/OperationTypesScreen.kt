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
fun OperationTypesScreen(navController: NavController) {
    ScreenContainer(
        title = "Операции",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MenuButton(
                    text = "Поступление",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Продажа",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Перемещение",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Списание",
                    onClick = { /* Handle click */ }
                )

                MenuButton(
                    text = "Инвентаризация",
                    onClick = { /* Handle click */ }
                )
            }
        }
    )
}