package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import java.util.Locale
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.PI

@Composable
fun WorkAngleScreen(navController: NavController) {
    ScreenContainer(
        title = "Work: W = F·d",
        onNextClick = { navController.popBackStack() }, content = {
            var mode by remember { mutableStateOf("W") }
            val modes = listOf("W", "F", "d", "θ")

            var inpA by remember { mutableStateOf("") }
            var inpB by remember { mutableStateOf("") }
            var inpC by remember { mutableStateOf("") }
            var result by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                                mode = m; inpA = ""; inpB = ""; inpC = ""; result = ""
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
                val (label1, label2, label3) = when (mode) {
                    "W"  -> listOf("F", "d", "θ (°)")
                    "F"  -> listOf("W", "d", "θ (°)")
                    "d"  -> listOf("W", "F", "θ (°)")
                    else -> listOf("W", "F", "d")  // θ is unknown here
                }

                Column(Modifier.fillMaxWidth(0.8f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = inpA, onValueChange = { inpA = it }, label = { Text(label1) }, placeholder = { Text("e.g. 50") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = inpB, onValueChange = { inpB = it }, label = { Text(label2) }, placeholder = { Text("e.g. 5") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = inpC, onValueChange = { inpC = it }, label = { Text(label3) }, placeholder = { Text(if (mode == "θ") "e.g. 30" else "e.g. 60") }, modifier = Modifier.fillMaxWidth())
                }

                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = {
                        val a = inpA.toDoubleOrNull() ?: 0.0
                        val b = inpB.toDoubleOrNull() ?: 0.0
                        val c = inpC.toDoubleOrNull() ?: 0.0
                        result = when(mode) {
                            "W" -> String.format(Locale.US, "W = %.4f J", a * b * cos(c * PI / 180))
                            "F" -> String.format(Locale.US, "F = %.4f N", if (b * cos(c * PI/180) != 0.0) a / (b * cos(c * PI/180)) else 0.0)
                            "d" -> String.format(Locale.US, "d = %.4f m", if (a * cos(c * PI/180) != 0.0) b / (a * cos(c * PI/180)) else 0.0)
                            else -> {
                                val denom = a * b
                                val ratio = if (denom != 0.0) a / denom else 0.0
                                val angleRad = acos(ratio)
                                val angleDeg = angleRad * 180 / PI
                                String.format(Locale.US, "θ = %.4f°", angleDeg)
                            }
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