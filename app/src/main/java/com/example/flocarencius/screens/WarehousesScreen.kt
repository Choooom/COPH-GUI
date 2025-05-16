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
import com.example.flocarencius.components.GridMenuButton
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria

@Composable
fun WarehousesScreen(navController: NavController) {
    ScreenContainer(
        title = "Склады",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Frictional Force",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = Inria,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 40.dp, top = 20.dp)
                )
                Text(
                    text = "What to solve for?",
                    fontFamily = Inria,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Kinetic Friction",
                        onClick = { navController.navigate("kinetic_friction") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "With Angle",
                        onClick = { navController.navigate("kinetic_friction_angle") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}