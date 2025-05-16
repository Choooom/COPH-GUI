package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flocarencius.components.MenuButton
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria

@Composable
fun HomeScreen(navController: NavController) {
    ScreenContainer(
        onNextClick = { navController.navigate("welcome") },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Computational Physics System",
                    fontFamily = Inria,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    text = "Choose from the 8 topics:",
                    fontFamily = Inria,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(0.8f).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MenuButton(
                        text = "Measurement",
                        onClick = { navController.navigate("nomenclature") }
                    )

                    MenuButton(
                        text = "Vectors",
                        onClick = { navController.navigate("counterparties") }
                    )

                    MenuButton(
                        text = "Kinematics",
                        onClick = { navController.navigate("price_types") }
                    )

                    MenuButton(
                        text = "Frictional Force",
                        onClick = { navController.navigate("warehouses") }
                    )

                    MenuButton(
                        text = "Work",
                        onClick = { navController.navigate("price_list") }
                    )

                    MenuButton(
                        text = "Apparent Weight",
                        onClick = { navController.navigate("work") }
                    )

                    MenuButton(
                        text = "Momentum, Impulse, & Collision",
                        onClick = { navController.navigate("operation_types") }
                    )

                    MenuButton(
                        text = "Temperature",
                        onClick = { navController.navigate("m_temperature") }
                    )
                }
            }
        }
    )
}
