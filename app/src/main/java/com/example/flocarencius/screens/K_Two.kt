package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KinematicsTwo(navController: NavController) {
    ScreenContainer(
        title = "Eq 2: Δt = t_f – t_i",
        onNextClick = { navController.popBackStack() },
        content =
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var mode by remember { mutableStateOf("Δt") }
            val modes = listOf("Δt", "t_f", "t_i")

            var inpA by remember { mutableStateOf("") }
            var inpB by remember { mutableStateOf("") }
            var result by remember { mutableStateOf("") }

            Text(
                text = "Result",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = result,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("—", textAlign = TextAlign.Center) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                modes.forEach { m ->
                    Button(
                        onClick = {
                            mode = m; inpA = ""; inpB = ""; result = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (mode == m) MaterialTheme.colorScheme.primary else Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(m, fontFamily = Inria, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            val (labelA, labelB) = when(mode) {
                "Δt" -> "t_f" to "t_i"
                "t_f" -> "Δt" to "t_i"
                else   -> "t_f" to "Δt"  // "t_i"
            }
            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = inpA,
                    onValueChange = { inpA = it },
                    label = { Text(labelA) },
                    placeholder = { Text("e.g. 5") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = inpB,
                    onValueChange = { inpB = it },
                    label = { Text(labelB) },
                    placeholder = { Text("e.g. 2") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val a = inpA.toDoubleOrNull() ?: 0.0
                    val b = inpB.toDoubleOrNull() ?: 0.0
                    result = when(mode) {
                        "Δt" -> String.format(Locale.US, "Δt = %.4f", a - b)
                        "t_f" -> String.format(Locale.US, "t_f = %.4f", a + b)
                        else   -> String.format(Locale.US, "t_i = %.4f", a - b)
                    }
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