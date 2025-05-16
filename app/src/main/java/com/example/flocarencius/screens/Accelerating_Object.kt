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
fun AcceleratingObjectScreen(navController: NavController, isOpposite: Boolean = false) {
    ScreenContainer(
        title = "Eq: N - W = m·a",
        onNextClick = { navController.popBackStack() }, content = {
        var mode by remember { mutableStateOf("N") }
        val modes = listOf("N", "W", "m", "a")

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
            // dynamic labels: other three variables
            val (label1, label2, label3) = when (mode) {
                "N"   -> listOf("W", "m", "a")
                "W"   -> listOf("N", "m", "a")
                "m"   -> listOf("N", "W", "a")
                else  -> listOf("N", "W", "m")  // a is the unknown
            }

            Column(Modifier.fillMaxWidth(0.8f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = inpA, onValueChange = { inpA = it }, label = { Text(label1) }, placeholder = { Text("e.g. 100 N") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = inpB, onValueChange = { inpB = it }, label = { Text(label2) }, placeholder = { Text("e.g. 50 N") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = inpC, onValueChange = { inpC = it }, label = { Text(label3) }, placeholder = { Text("e.g. 10 kg") }, modifier = Modifier.fillMaxWidth())
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    val aVal = inpA.toDoubleOrNull() ?: 0.0
                    val bVal = inpB.toDoubleOrNull() ?: 0.0
                    val cVal = inpC.toDoubleOrNull() ?: 0.0
                    result = when(mode) {
                        "N" -> String.format(Locale.US, "N = %.4f N", aVal + (if (isOpposite) -1 else 1) * (cVal * bVal))
                        "W" -> String.format(Locale.US, "W = %.4f N", aVal - cVal * bVal)
                        "m" -> String.format(Locale.US, "m = %.4f kg", if (cVal != 0.0) (aVal - bVal) / cVal else 0.0)
                        else -> String.format(Locale.US, "a = %.4f m/s²", if (cVal != 0.0) (aVal - bVal) / cVal else 0.0)
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
