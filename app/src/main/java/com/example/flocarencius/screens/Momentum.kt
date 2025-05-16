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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MomentumScreen(navController: NavController) {
    ScreenContainer(
        title = "Momentum: p = m·v",
        onNextClick = { navController.popBackStack() }, content =
    {
        var mode by remember { mutableStateOf("p") }
        val modes = listOf("p", "m", "v")
        var massUnit by remember { mutableStateOf("kg") }

        var inpA by remember { mutableStateOf("") }
        var inpB by remember { mutableStateOf("") }
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
            // Mode selector
            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                modes.forEach { m ->
                    Button(
                        onClick = { mode = m; inpA = ""; inpB = ""; result = "" },
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
            // Unit selector for mass
            Text("Mass Unit", fontWeight = FontWeight.Medium)
            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("g", "kg").forEach { u ->
                    Button(
                        onClick = { massUnit = u },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (massUnit == u) MaterialTheme.colorScheme.primary else Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(u, fontFamily = Inria, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            // Input fields
            val (labelA, labelB, hintB) = when(mode) {
                "p" -> listOf("m ($massUnit)", "v (m/s)", "e.g. 10")
                "m" -> listOf("p (kg·m/s)", "v (m/s)", "e.g. 5")
                else -> listOf("p (kg·m/s)", "m ($massUnit)", "e.g. 2")
            }

            Column(Modifier.fillMaxWidth(0.8f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = inpA,
                    onValueChange = { inpA = it },
                    label = { Text(labelA) },
                    placeholder = { Text(if (mode == "p") "e.g. 50" else "e.g. 10") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = inpB,
                    onValueChange = { inpB = it },
                    label = { Text(labelB) },
                    placeholder = { Text(hintB) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(32.dp))
            // Compute
            Button(
                onClick = {
                    val a = inpA.toDoubleOrNull() ?: 0.0
                    val b = inpB.toDoubleOrNull() ?: 0.0
                    when(mode) {
                        "p" -> {
                            // m in grams to kg if needed
                            val mass = if (massUnit == "g") a / 1000 else a
                            val p = mass * b
                            result = String.format(Locale.US, "p = %.4f %s·m/s", p, massUnit)
                        }
                        "m" -> {
                            val pVal = if (mode == "m") a else a // a holds p
                            val mass = if (massUnit == "g") (pVal / b) * 1000 else pVal / b
                            result = String.format(Locale.US, "m = %.4f %s", mass, massUnit)
                        }
                        else -> {
                            val pVal = a
                            val mass = if (massUnit == "g") b / 1000 else b
                            val v = pVal / mass
                            result = String.format(Locale.US, "v = %.4f m/s", v)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.5f).height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Compute", fontFamily = Inria, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
    )
}