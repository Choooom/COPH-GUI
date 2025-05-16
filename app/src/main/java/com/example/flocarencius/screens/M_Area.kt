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

object AreaConversionUtils {

    // 1) Supported units
    val units = listOf(
        "Square Meter",
        "Square Kilometer",
        "Square Centimeter",
        "Square Millimeter",
        "Square Micrometer",
        "Hectare",
        "Square Mile",
        "Square Yard",
        "Square Foot",
        "Square Inch",
        "Acre"
    )

    // 2) Symbols for labels
    val symbols = mapOf(
        "Square Meter"      to "m²",
        "Square Kilometer"  to "km²",
        "Square Centimeter" to "cm²",
        "Square Millimeter" to "mm²",
        "Square Micrometer" to "μm²",
        "Hectare"           to "ha",
        "Square Mile"       to "mi²",
        "Square Yard"       to "yd²",
        "Square Foot"       to "ft²",
        "Square Inch"       to "in²",
        "Acre"              to "ac"
    )

    // 3) Factors to convert *to* square meters
    private val toSquareMeter = mapOf(
        "Square Meter"      to 1.0,
        "Square Kilometer"  to 1e6,
        "Square Centimeter" to 1e-4,
        "Square Millimeter" to 1e-6,
        "Square Micrometer" to 1e-12,
        "Hectare"           to 1e4,
        "Square Mile"       to 2.589988e6,
        "Square Yard"       to 0.83612736,
        "Square Foot"       to 0.09290304,
        "Square Inch"       to 0.00064516,
        "Acre"              to 4_046.8564224
    )

    /** Convert [value] in [fromUnit] to [toUnit]. */
    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        val inSqM = value * (toSquareMeter[fromUnit] ?: error("Unknown unit $fromUnit"))
        return inSqM / (toSquareMeter[toUnit] ?: error("Unknown unit $toUnit"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Area",
        onNextClick = { navController.popBackStack() },
        content =
    {
        var fromText         by remember { mutableStateOf("") }
        var selectedFromUnit by remember { mutableStateOf("Square Meter") }
        var selectedToUnit   by remember { mutableStateOf("Square Kilometer") }

        val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
            derivedStateOf {
                fromText.toDoubleOrNull()
                    ?.let { AreaConversionUtils.convert(it, selectedFromUnit, selectedToUnit) }
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
                text = "Area",
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
                        val sym = AreaConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                        Text("From ($sym)", color = Color.White)
                    },
                    placeholder = { Text("e.g. 10", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = resultText,
                    onValueChange = { /* no-op */ },
                    label = {
                        val sym = AreaConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
                        Text("To ($sym)", color = Color.White)
                    },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Unit selectors
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(AreaConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedFromUnit
                        ) { selectedFromUnit = unit }
                    }
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(AreaConversionUtils.units) { unit ->
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