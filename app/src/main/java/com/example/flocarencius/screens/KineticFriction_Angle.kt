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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaticFrictionScreen(navController: NavController) {
    ScreenContainer(
        title = "Static Friction",
        onNextClick = { navController.popBackStack() }, content={
        var mode by remember { mutableStateOf("F_min") }
        val modes = listOf("F_min", "F_max", "μ", "N")

        var unitMass by remember { mutableStateOf("kg") }
        var inpA by remember { mutableStateOf("") }
        var inpB by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        // constants
        val theta = 45 * PI / 180.0
        val g = 9.81

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
            // Mass unit picker
            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("kg", "g").forEach { u ->
                    Button(
                        onClick = { unitMass = u },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (unitMass == u) MaterialTheme.colorScheme.primary else Color.DarkGray
                        ),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(u, fontFamily = Inria, color = Color.White, fontWeight = FontWeight.Normal, fontSize = 16.sp)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            // Input fields
            val (labelA, labelB, enabledB) = when (mode) {
                "F_min" -> Triple("m ($unitMass)", "μ_s", true)
                "F_max" -> Triple("m ($unitMass)", "μ_s", true)
                "μ"     -> Triple("F (N)", "N (N)", true)
                else    -> Triple("m ($unitMass)", "—", false)
            }

            Row(
                Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = inpA,
                    onValueChange = { inpA = it },
                    label = { Text(labelA) },
                    placeholder = {
                        Text(
                            if (labelA.startsWith("m")) "e.g. 10"
                            else if (labelA.startsWith("F")) "e.g. 15"
                            else "e.g. 0.5"
                        )
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    singleLine = true
                )

                OutlinedTextField(
                    value = inpB,
                    onValueChange = { if (enabledB) inpB = it },
                    label = { Text(labelB) },
                    placeholder = {
                        Text(if (enabledB) {
                            if (labelB.startsWith("μ")) "e.g. 0.5"
                            else if (labelB.startsWith("N")) "e.g. 30"
                            else "e.g. 20"
                        } else "—")
                    },
                    readOnly = !enabledB,
                    enabled = enabledB,
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    singleLine = true
                )
            }

            Spacer(Modifier.height(32.dp))
            // Compute
            Button(
                onClick = {
                    // parse inputs
                    val mInput = inpA.toDoubleOrNull() ?: 0.0
                    val m = if (unitMass == "g") mInput / 1000.0 else mInput
                    val us = inpB.toDoubleOrNull() ?: 0.0
                    val F_in = inpA.toDoubleOrNull() ?: 0.0
                    val N_in = inpB.toDoubleOrNull() ?: 0.0
                    val mg = m * g
                    val N0 = mg * cos(theta)
                    result = when (mode) {
                        "F_min" -> String.format(Locale.US, "F_min = %.4f N", mg * sin(theta) - us * N0)
                        "F_max" -> String.format(Locale.US, "F_max = %.4f N", mg * sin(theta) + us * N0)
                        "μ"     -> String.format(Locale.US, "μ_s = %.4f", if (N_in != 0.0) (F_in - mg * sin(theta)) / N_in else 0.0)
                        else     -> String.format(Locale.US, "N = %.4f N", N0)
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