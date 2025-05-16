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

object VolumeConversionUtils {

    // 1) Supported units
    val units = listOf(
        "Cubic Meter",
        "Cubic Kilometer",
        "Cubic Centimeter",
        "Cubic Millimeter",
        "Liter",
        "Milliliter",
        "US Gallon",
        "US Quart",
        "US Pint",
        "US Fluid Ounce",
        "Imperial Gallon",
        "Imperial Quart",
        "Imperial Pint",
        "Imperial Fluid Ounce",
        "Cubic Mile",
        "Cubic Yard",
        "Cubic Foot",
        "Cubic Inch"
    )

    // 2) Unit symbols for labels
    val symbols = mapOf(
        "Cubic Meter" to "m³",
        "Cubic Kilometer" to "km³",
        "Cubic Centimeter" to "cm³",
        "Cubic Millimeter" to "mm³",
        "Liter" to "L",
        "Milliliter" to "mL",
        "US Gallon" to "gal(US)",
        "US Quart" to "qt(US)",
        "US Pint" to "pt(US)",
        "US Fluid Ounce" to "fl oz(US)",
        "Imperial Gallon" to "gal(Imp)",
        "Imperial Quart" to "qt(Imp)",
        "Imperial Pint" to "pt(Imp)",
        "Imperial Fluid Ounce" to "fl oz(Imp)",
        "Cubic Mile" to "mi³",
        "Cubic Yard" to "yd³",
        "Cubic Foot" to "ft³",
        "Cubic Inch" to "in³"
    )

    // 3) Factors to convert *to* cubic meters
    private val toCubicMeter = mapOf(
        "Cubic Meter" to 1.0,
        "Cubic Kilometer" to 1e9,
        "Cubic Centimeter" to 1e-6,
        "Cubic Millimeter" to 1e-9,
        "Liter" to 1e-3,
        "Milliliter" to 1e-6,
        "US Gallon" to 0.003785411784,
        "US Quart" to 0.000946352946,
        "US Pint" to 0.000473176473,
        "US Fluid Ounce" to 2.95735295625e-5,
        "Imperial Gallon" to 0.00454609,
        "Imperial Quart" to 0.0011365225,
        "Imperial Pint" to 0.00056826125,
        "Imperial Fluid Ounce" to 2.84130625e-5,
        "Cubic Mile" to 4.16818183e9,
        "Cubic Yard" to 0.76455485798,
        "Cubic Foot" to 0.028316846592,
        "Cubic Inch" to 1.6387064e-5
    )

    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        val inCubicMeters = value * (toCubicMeter[fromUnit] ?: error("Unknown unit $fromUnit"))
        return inCubicMeters / (toCubicMeter[toUnit] ?: error("Unknown unit $toUnit"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Volume",
        onNextClick = { navController.popBackStack() },
        content = {
        var fromText by remember { mutableStateOf("") }
        var selectedFromUnit by remember { mutableStateOf("Liter") }
        var selectedToUnit by remember   { mutableStateOf("Milliliter") }

        val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
            derivedStateOf {
                fromText.toDoubleOrNull()
                    ?.let {
                        VolumeConversionUtils.convert(
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
                text = "Volume",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = Inria,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, bottom = 40.dp)
            )

            // From / To fields
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = fromText,
                    onValueChange = { fromText = it },
                    label = {
                        val sym = VolumeConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                        Text("From ($sym)", color = Color.White)
                    },
                    placeholder = { Text("e.g. 1.5", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = resultText,
                    onValueChange = { /* no‐op */ },
                    label = {
                        val sym = VolumeConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
                        Text("To ($sym)", color = Color.White)
                    },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // Two-unit lists
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(VolumeConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedFromUnit
                        ) { selectedFromUnit = unit }
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(VolumeConversionUtils.units) { unit ->
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

