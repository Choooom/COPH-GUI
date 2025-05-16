package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria
import java.util.Locale

// 1) Calculate Work: W = F · d
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkCalculateScreen(navController: NavController) {
    ScreenContainer(
        title = "Work: W = F·d",
        onNextClick = { navController.popBackStack() }, content = {
        var force by remember { mutableStateOf("") }
        var distance by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Result", style = MaterialTheme.typography.headlineSmall, fontFamily = Inria, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
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
            OutlinedTextField(
                value = force,
                onValueChange = { force = it },
                label = { Text("Force (F)") },
                placeholder = { Text("e.g. 10 N") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (d)") },
                placeholder = { Text("e.g. 5 m") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    val f = force.toDoubleOrNull() ?: 0.0
                    val d = distance.toDoubleOrNull() ?: 0.0
                    result = String.format(Locale.US, "W = %.4f J", f * d)
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

// 2) Calculate Force: F = W / d
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceCalculateScreen(navController: NavController) {
    ScreenContainer(
        title = "Force: F = W/d",
        onNextClick = { navController.popBackStack() }, content = {
        var work by remember { mutableStateOf("") }
        var distance by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Result", style = MaterialTheme.typography.headlineSmall, fontFamily = Inria, fontWeight = FontWeight.Bold)
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
            OutlinedTextField(
                value = work,
                onValueChange = { work = it },
                label = { Text("Work (W)") },
                placeholder = { Text("e.g. 50 J") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (d)") },
                placeholder = { Text("e.g. 5 m") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    val w = work.toDoubleOrNull() ?: 0.0
                    val d = distance.toDoubleOrNull() ?: 0.0
                    val f = if (d != 0.0) w / d else 0.0
                    result = String.format(Locale.US, "F = %.4f N", f)
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

// 3) Calculate Distance: d = W / F
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistanceCalculateScreen(navController: NavController) {
    ScreenContainer(
        title = "Distance: d = W/F",
        onNextClick = { navController.popBackStack() },
        content = {
        var work by remember { mutableStateOf("") }
        var force by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Result", style = MaterialTheme.typography.headlineSmall, fontFamily = Inria, fontWeight = FontWeight.Bold)
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
            OutlinedTextField(
                value = work,
                onValueChange = { work = it },
                label = { Text("Work (W)") },
                placeholder = { Text("e.g. 50 J") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = force,
                onValueChange = { force = it },
                label = { Text("Force (F)") },
                placeholder = { Text("e.g. 10 N") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    val w = work.toDoubleOrNull() ?: 0.0
                    val f = force.toDoubleOrNull() ?: 0.0
                    val d = if (f != 0.0) w / f else 0.0
                    result = String.format(Locale.US, "d = %.4f m", d)
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
