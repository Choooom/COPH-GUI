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
fun NomenclatureScreen(navController: NavController) {
    ScreenContainer(
        title = "Номенклатура",
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
                        text = "Length",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Volume",
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
                        text = "Temperature",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Weight",
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
                        text = "Area",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Time",
                        onClick = { /* Handle click */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}