package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flocarencius.components.GridMenuButton
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria

@Composable
fun NomenclatureScreen(navController: NavController) {
    ScreenContainer(
        title = "Номенклатура",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text = "Measurements",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = Inria,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 40.dp, top = 20.dp)
                    )
                    Text(
                        text = "Choose from the \n6 categories:",
                        fontFamily = Inria,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Length",
                        onClick = { navController.navigate("m_length") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Volume",
                        onClick = { navController.navigate("m_volume") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Temperature",
                        onClick = { navController.navigate("m_temperature") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Weight",
                        onClick = { navController.navigate("m_weight") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "Area",
                        onClick = { navController.navigate("m_area") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "Time",
                        onClick = { navController.navigate("m_time") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}