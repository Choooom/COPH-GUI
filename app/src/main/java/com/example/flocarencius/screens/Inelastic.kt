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
fun InelasticCollisionScreen(navController: NavController) {
    ScreenContainer(
        title = "Inelastic: m1v1 + m2v2 = (m1+m2)v_f",
        onNextClick = { navController.popBackStack() }, content = {
        var mode by remember { mutableStateOf("v_f") }
        val modes = listOf("m1","v1","m2","v2","v_f")
        var massUnit by remember { mutableStateOf("kg") }

        var inp1 by remember { mutableStateOf("") }
        var inp2 by remember { mutableStateOf("") }
        var inp3 by remember { mutableStateOf("") }
        var inp4 by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Result", style=MaterialTheme.typography.headlineSmall, fontFamily=Inria, fontWeight=FontWeight.Bold)
            OutlinedTextField(result, {}, readOnly=true,
                placeholder={Text("—", textAlign=TextAlign.Center)}, modifier=Modifier.fillMaxWidth(0.8f).height(56.dp),
                textStyle=LocalTextStyle.current.copy(textAlign=TextAlign.Center))
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                modes.forEach { m ->
                    Button(onClick={mode=m; inp1=""; inp2=""; inp3=""; inp4=""; result=""},
                        colors=ButtonDefaults.buttonColors(containerColor=if(mode==m) MaterialTheme.colorScheme.primary else Color.DarkGray),
                        modifier=Modifier.weight(1f), shape=RoundedCornerShape(8.dp)) { Text(m, fontFamily=Inria, color=Color.White, fontSize=16.sp) }
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("Mass Unit", fontWeight=FontWeight.Medium)
            Row(Modifier.fillMaxWidth(0.8f), horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                listOf("g","kg").forEach { u ->
                    Button(onClick={ massUnit=u }, colors=ButtonDefaults.buttonColors(containerColor=if(massUnit==u) MaterialTheme.colorScheme.primary else Color.DarkGray),
                        modifier=Modifier.weight(1f).height(48.dp), shape=RoundedCornerShape(8.dp)) { Text(u, fontFamily=Inria, color=Color.White, fontSize=16.sp) }
                }
            }
            Spacer(Modifier.height(24.dp))
            val labels = when(mode) {
                "m1" -> listOf("v1 (m/s)","m2 ($massUnit)","v2 (m/s)","v_f (m/s)")
                "v1" -> listOf("m1 ($massUnit)","m2 ($massUnit)","v2 (m/s)","v_f (m/s)")
                "m2" -> listOf("m1 ($massUnit)","v1 (m/s)","v2 (m/s)","v_f (m/s)")
                "v2" -> listOf("m1 ($massUnit)","v1 (m/s)","m2 ($massUnit)","v_f (m/s)")
                else  -> listOf("m1 ($massUnit)","v1 (m/s)","m2 ($massUnit)","v2 (m/s)")
            }
            Column(Modifier.fillMaxWidth(0.8f), verticalArrangement=Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(inp1,{inp1=it},label={Text(labels[0])},placeholder={Text("e.g. 1000")},modifier=Modifier.fillMaxWidth())
                OutlinedTextField(inp2,{inp2=it},label={Text(labels[1])},placeholder={Text("e.g. 20")},modifier=Modifier.fillMaxWidth())
                OutlinedTextField(inp3,{inp3=it},label={Text(labels[2])},placeholder={Text("e.g. 3000")},modifier=Modifier.fillMaxWidth())
                OutlinedTextField(inp4,{inp4=it},label={Text(labels[3])},placeholder={Text("e.g. 0")},modifier=Modifier.fillMaxWidth())
            }
            Spacer(Modifier.height(32.dp))
            Button(onClick={
                val a=inp1.toDoubleOrNull()?:0.0; val b=inp2.toDoubleOrNull()?:0.0; val c=inp3.toDoubleOrNull()?:0.0; val d=inp4.toDoubleOrNull()?:0.0
                val m1 = if(massUnit=="g") a/1000 else a
                val m2 = if(massUnit=="g") c/1000 else c
                val v1 = if(mode=="v1") a else b
                val v2 = if(mode=="v2") c else d
                val vf = if(mode=="v_f") d else d
                result = when(mode) {
                    "v_f" -> String.format(Locale.US,"v_f = %.4f m/s", (m1*v1 + m2*v2)/(m1+m2))
                    "m1" -> String.format(Locale.US,"m1 = %.4f %s", ((m2*(d-v2))/(v1-d))* if(massUnit=="g") 1000 else 1, massUnit)
                    "m2" -> String.format(Locale.US,"m2 = %.4f %s", ((m1*(d-v1))/(d-v2))* if(massUnit=="g") 1000 else 1, massUnit)
                    "v1" -> String.format(Locale.US,"v1 = %.4f m/s", ((m1+m2)*d - m2*v2)/m1)
                    else  -> String.format(Locale.US,"v2 = %.4f m/s", ((m1+m2)*d - m1*v1)/m2)
                }
            },modifier=Modifier.fillMaxWidth(0.5f).height(48.dp),shape=RoundedCornerShape(12.dp)) { Text("Compute",fontFamily=Inria,fontWeight=FontWeight.Bold,fontSize=18.sp) }
        }
    }
    )
}
