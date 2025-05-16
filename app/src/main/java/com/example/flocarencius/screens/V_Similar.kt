package com.example.flocarencius.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VectorSimilarScreen(navController: NavController) {
    ScreenContainer(
        title = "Vectors: Similar",
        onNextClick = { navController.popBackStack() },
        content =
    {
        // State
        var firstText by remember { mutableStateOf("") }
        var secondText by remember { mutableStateOf("") }
        var selectedUnit by remember { mutableStateOf("m") }
        var selectedDir  by remember { mutableStateOf("E") }
        var resultText  by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Similar",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            // Result header + field
            Text(
                text = "Final Displacement",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = resultText,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("—", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            Spacer(Modifier.height(24.dp))

            // Unit selector row
            Text("Unit", fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("m", "km").forEach { u ->
                    Button(
                        onClick = { selectedUnit = u },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedUnit == u) MaterialTheme.colorScheme.primary else Color.DarkGray
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(u, color = Color.White, fontFamily = Inria, fontWeight = FontWeight.Normal, fontSize = 20.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Direction selector row
            Text("Direction", fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listOf("N","E","W","S").forEach { d ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = if (selectedDir == d)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                else Color.DarkGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedDir = d }
                    ) {
                        Text(d, color = Color.White, fontFamily = Inria, fontSize = 18.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Movement inputs
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = firstText,
                    onValueChange = { firstText = it },
                    label = { Text("1st Movement", color = Color.White) },
                    placeholder = { Text("e.g. 5", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = secondText,
                    onValueChange = { secondText = it },
                    label = { Text("2nd Movement", color = Color.White) },
                    placeholder = { Text("e.g. 10", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            // Compute button
            Button(
                onClick = {
                    val a = firstText.toDoubleOrNull() ?: 0.0
                    val b = secondText.toDoubleOrNull() ?: 0.0
                    val sum = a + b
                    resultText = "%,.6g %s, %s".format(sum, selectedUnit, directionFull(selectedDir))
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Compute", fontFamily = Inria, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
    )
}

// helper to map single-letter to full direction name
private fun directionFull(d: String) = when(d) {
    "N" -> "North"
    "E" -> "East"
    "W" -> "West"
    "S" -> "South"
    else -> ""
}
