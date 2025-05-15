package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flocarencius.components.GridMenuButton
import com.example.flocarencius.components.ScreenContainer

@Composable
fun PriceTypesScreen(navController: NavController) {
    ScreenContainer(
        title = "Типы цен",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Добавить",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Редактировать",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Удалить",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Поиск",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}