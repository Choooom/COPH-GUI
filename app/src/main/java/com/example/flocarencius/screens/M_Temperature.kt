package com.example.flocarencius.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

object TemperatureConversionUtils {

    // 1) Supported units
    val units = listOf(
        "Celsius",
        "Kelvin",
        "Fahrenheit"
    )

    // 2) Symbols for labels
    val symbols = mapOf(
        "Celsius"    to "°C",
        "Kelvin"     to "K",
        "Fahrenheit" to "°F"
    )

    /** Converts [value] in [fromUnit] to [toUnit]. */
    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        // first normalize to Kelvin
        val inKelvin = when (fromUnit) {
            "Celsius"    -> value + 273.15
            "Kelvin"     -> value
            "Fahrenheit" -> (value - 32.0) * 5.0 / 9.0 + 273.15
            else         -> error("Unknown unit $fromUnit")
        }
        // then convert Kelvin → target
        return when (toUnit) {
            "Celsius"    -> inKelvin - 273.15
            "Kelvin"     -> inKelvin
            "Fahrenheit" -> (inKelvin - 273.15) * 9.0 / 5.0 + 32.0
            else         -> error("Unknown unit $toUnit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Temperature",
        onNextClick = { navController.popBackStack() },
        content = {
        var fromText        by remember { mutableStateOf("") }
        var selectedFromUnit by remember { mutableStateOf("Celsius") }
        var selectedToUnit   by remember { mutableStateOf("Fahrenheit") }

        val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
            derivedStateOf {
                fromText.toDoubleOrNull()
                    ?.let {
                        TemperatureConversionUtils.convert(
                            it,
                            selectedFromUnit,
                            selectedToUnit
                        )
                    }
                    ?.let { "%,.6g".format(it) }
                    ?: ""
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Temperature",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
            )

            // From / To inputs
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = fromText,
                    onValueChange = { fromText = it },
                    label = {
                        val sym = TemperatureConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                        Text("From ($sym)", color = Color.White)
                    },
                    placeholder = { Text("e.g. 100", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = resultText,
                    onValueChange = { /* read-only */ },
                    label = {
                        val sym = TemperatureConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
                        Text("To ($sym)", color = Color.White)
                    },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Unit selectors (only three items, but we keep the same pattern)
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(TemperatureConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedFromUnit
                        ) { selectedFromUnit = unit }
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(TemperatureConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedToUnit
                        ) { selectedToUnit = unit }
                    }
                }
            }
        }
        }
    )
}

@Composable
private fun UnitRow(
    unit: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isSelected)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    else
        Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .background(bg, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = unit,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}