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
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoilScreen(navController: NavController) {
    ScreenContainer(
        title = "Recoil: m_c v_c + m_b v_b = 0",
        onNextClick = { navController.popBackStack() }, content = {
        var mode by remember { mutableStateOf("m_c") }
        val modes = listOf("m_c", "v_c", "m_b", "v_b")
        var massUnit by remember { mutableStateOf("kg") }

        var inpA by remember { mutableStateOf("") }
        var inpB by remember { mutableStateOf("") }
        var inpC by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(
            Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Result", style = MaterialTheme.typography.headlineSmall, fontFamily = Inria, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = result, onValueChange = {}, readOnly = true,
                placeholder = { Text("—", textAlign = TextAlign.Center) },
                modifier = Modifier.fillMaxWidth(0.8f).height(56.dp),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                modes.forEach { m ->
                    Button(onClick = { mode = m; inpA = ""; inpB = ""; inpC = ""; result = "" },
                        colors = ButtonDefaults.buttonColors(containerColor = if(mode==m) MaterialTheme.colorScheme.primary else Color.DarkGray),
                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)) {
                        Text(m, fontFamily = Inria, color=Color.White, fontSize=16.sp)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("Mass Unit", fontWeight = FontWeight.Medium)
            Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("g","kg").forEach { u ->
                    Button(onClick = { massUnit = u },
                        colors = ButtonDefaults.buttonColors(containerColor = if(massUnit==u) MaterialTheme.colorScheme.primary else Color.DarkGray),
                        modifier = Modifier.weight(1f).height(48.dp), shape = RoundedCornerShape(8.dp)) {
                        Text(u, fontFamily = Inria, color=Color.White, fontSize=16.sp)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            val (l1, l2, l3, ph3) = when(mode) {
                "m_c" -> listOf("v_c (m/s)", "m_b ($massUnit)", "v_b (m/s)", "e.g. 100")
                "v_c" -> listOf("m_c ($massUnit)", "m_b ($massUnit)", "v_b (m/s)", "e.g. 10")
                "m_b" -> listOf("m_c ($massUnit)", "v_c (m/s)", "v_b (m/s)", "e.g. 20")
                else  -> listOf("m_c ($massUnit)", "v_c (m/s)", "m_b ($massUnit)", "e.g. 5")
            }

            Column(Modifier.fillMaxWidth(0.8f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(inpA, {inpA=it}, label={Text(l1)}, placeholder={Text("e.g. 5")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(inpB, {inpB=it}, label={Text(l2)}, placeholder={Text("e.g. 100")}, modifier=Modifier.fillMaxWidth())
                OutlinedTextField(inpC, {inpC=it}, label={Text(l3)}, placeholder={Text(ph3)}, modifier=Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(32.dp))
            Button(onClick = {
                val a = inpA.toDoubleOrNull()?:0.0
                val b = inpB.toDoubleOrNull()?:0.0
                val c = inpC.toDoubleOrNull()?:0.0
                result = when(mode) {
                    "m_c" -> String.format(Locale.US,"m_c = %.4f %s", abs(b*c/a), massUnit)
                    "v_c" -> String.format(Locale.US,"v_c = %.4f m/s", -b*c/a)
                    "m_b" -> String.format(Locale.US,"m_b = %.4f %s", abs(a*c/b), massUnit)
                    else   -> String.format(Locale.US,"v_b = %.4f m/s", -a*c/b)
                }
            }, modifier=Modifier.fillMaxWidth(0.5f).height(48.dp), shape=RoundedCornerShape(12.dp)) {
                Text("Compute", fontFamily=Inria, fontWeight=FontWeight.Bold, fontSize=18.sp)
            }
        }
    }
    )
}