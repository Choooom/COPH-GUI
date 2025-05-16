// VectorWithAngleScreen.kt
package com.example.flocarencius.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VectorWithAngleScreen(navController: NavController) {
    ScreenContainer(
        title = "Vectors: With Angle",
        onNextClick = { navController.popBackStack() },
        content =
    {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally){
            var mode by remember { mutableStateOf("Resultant") }
            val modes = listOf("Horizontal", "Vertical", "Resultant")

            // Inputs
            var inpA by remember { mutableStateOf("") }
            var inpB by remember { mutableStateOf("") }
            var dirH by remember { mutableStateOf("E") }  // horizontal direction
            var dirV by remember { mutableStateOf("N") }  // vertical direction
            var unit by remember { mutableStateOf("m") }  // unit selector
            var result by remember { mutableStateOf("") }

            Text(
                text = "With Angle",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Text(
                text = "Final Displacement",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = result, onValueChange = {},
                readOnly = true,
                placeholder = { Text("—", textAlign = TextAlign.Center) },
                modifier = Modifier.fillMaxWidth(0.8f)
                    .height(56.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                // 1) Mode selector
                Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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
                            Text(m.take(3), fontFamily = Inria, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))


                Text("Unit", fontWeight = FontWeight.Medium)
                Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    listOf("m", "km").forEach { u ->
                        Button(
                            onClick = { unit = u },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (unit == u) MaterialTheme.colorScheme.primary else Color.DarkGray
                            ),
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(u, fontFamily = Inria, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 3) Input fields
                val (labelA, labelB) = when (mode) {
                    "Horizontal", "Vertical" -> "Resultant (R)" to "Angle (°)"
                    else                     -> "Horizontal" to "Vertical"
                }
                Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = inpA, onValueChange = { inpA = it },
                        label = { Text(labelA, color = Color.White) },
                        placeholder = { Text("e.g. 10", color = Color.White.copy(alpha = 0.6f)) },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = inpB, onValueChange = { inpB = it },
                        label = { Text(labelB, color = Color.White) },
                        placeholder = { Text("e.g. 30", color = Color.White.copy(alpha = 0.6f)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(24.dp))

                // 4) Direction selectors
                if (mode == "Resultant") {
                    Text("Horizontal Direction", fontWeight = FontWeight.Medium)
                    DirectionRow(selected = dirH, onSelect = { dirH = it })
                    Spacer(Modifier.height(12.dp))
                    Text("Vertical Direction", fontWeight = FontWeight.Medium)
                    DirectionRow(selected = dirV, onSelect = { dirV = it })
                } else {
                    Text("${mode} Dir", fontWeight = FontWeight.Medium)
                    DirectionRow(
                        selected = if (mode=="Horizontal") dirH else dirV,
                        onSelect = { d ->
                            if (mode=="Horizontal") dirH = d else dirV = d
                        }
                    )
                }

                Spacer(Modifier.height(32.dp))

                // 5) Compute
                Button(
                    onClick = {
                        val a = inpA.toDoubleOrNull() ?: 0.0
                        val b = inpB.toDoubleOrNull() ?: 0.0
                        result = when (mode) {
                            "Horizontal" -> {
                                val h = a * cos(b * PI/180)
                                String.format(Locale.US, "%.4f %s %s", h, unit, dirH)
                            }
                            "Vertical" -> {
                                val v = a * sin(b * PI/180)
                                String.format(Locale.US, "%.4f %s %s", v, unit, dirV)
                            }
                            else -> {
                                val r = sqrt(a*a + b*b)
                                val θ = atan2(b, a) * 180/PI
                                // Format with four decimal places, include unit & directions:
                                // e.g. "15.0000 m, 30.0000° North of East"
                                String.format(
                                    Locale.US,
                                    "%.4f %s, %.4f° %s of %s",
                                    r, unit,
                                    θ,
                                    directionFull(dirV),
                                    directionFull(dirH)
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Compute", fontFamily = Inria, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(Modifier.height(24.dp))

                // 6) Result display
            }
        }

    }
    )
}

@Composable
private fun DirectionRow(selected: String, onSelect: (String)->Unit) {
    Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("N","E","S","W").forEach { d ->
            Box(
                Modifier
                    .size(48.dp)
                    .background(
                        color = if (d==selected) MaterialTheme.colorScheme.primary.copy(alpha=0.8f) else Color.DarkGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelect(d) },
                contentAlignment = Alignment.Center
            ) {
                Text(d, color = Color.White, fontFamily = Inria, fontSize = 18.sp)
            }
        }
    }
}

// helper to map single-letter to full direction name
private fun directionFull(d: String) = when(d) {
    "N" -> "North"
    "E" -> "East"
    "W" -> "West"
    "S" -> "South"
    else -> ""
}
