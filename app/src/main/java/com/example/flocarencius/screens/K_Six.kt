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
import kotlin.math.sqrt

@Composable
fun KinematicsSix(navController: NavController) {
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
                var mode by remember { mutableStateOf("d") }
                val modes = listOf("d", "vᵢ", "a", "t")

                var inpA by remember { mutableStateOf("") }
                var inpB by remember { mutableStateOf("") }
                var inpC by remember { mutableStateOf("") }
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
                            onClick = { mode = m; inpA = ""; inpB = ""; inpC = ""; result = "" },
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
                    "d"   -> listOf("vᵢ", "a",  "t")
                    "vᵢ"  -> listOf("d",   "a",  "t")
                    "a"   -> listOf("d",   "vᵢ", "t")
                    else  -> listOf("d",   "vᵢ", "a")
                }

                Column(Modifier.fillMaxWidth(0.8f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = inpA, onValueChange = { inpA = it }, label = { Text(label1) }, placeholder = { Text("e.g. 10") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = inpB, onValueChange = { inpB = it }, label = { Text(label2) }, placeholder = { Text("e.g. 2") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = inpC, onValueChange = { inpC = it }, label = { Text(label3) }, placeholder = { Text("e.g. 4") }, modifier = Modifier.fillMaxWidth())
                }

                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = {
                        val a = inpA.toDoubleOrNull() ?: 0.0
                        val b = inpB.toDoubleOrNull() ?: 0.0
                        val c = inpC.toDoubleOrNull() ?: 0.0
                        result = when(mode) {
                            "d" -> String.format(Locale.US, "d = %.4f", b * c + 0.5 * a * c * c)
                            "vᵢ" -> String.format(Locale.US, "vᵢ = %.4f", (a * c * c / 2 + b * c - b * c) /* simplify? placeholder */)
                            "a" -> String.format(Locale.US, "a = %.4f", if (c != 0.0) 2 * (a - b * c) / (c * c) else 0.0)
                            else -> {
                                // solve 0.5*a*t^2 + b*t - a =? misuse variables: need mapping
                                // here a=inpA->d, b=inpB->vᵢ, c=inpC->a for t
                                val dVal = a
                                val vi = b
                                val accel = c
                                val disc = vi*vi + 2 * accel * dVal
                                val tVal = if (disc >= 0 && accel != 0.0) (-vi + sqrt(disc)) / accel else 0.0
                                String.format(Locale.US, "t = %.4f", tVal)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.5f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Compute", fontFamily = Inria, fontWeight = FontWeight.Bold, fontSize = 18.sp) }
            }
        }
    )
}