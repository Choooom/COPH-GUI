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

object WeightConversionUtils {

    // 1) Supported units
    val units = listOf(
        "Kilogram",
        "Gram",
        "Milligram",
        "Metric Ton",
        "Pound",
        "Ounce",
        "Carat"
    )

    // 2) Symbols for labels
    val symbols = mapOf(
        "Kilogram"   to "kg",
        "Gram"       to "g",
        "Milligram"  to "mg",
        "Metric Ton" to "t",
        "Pound"      to "lb",
        "Ounce"      to "oz",
        "Carat"      to "ct"
    )

    // 3) Factors to convert *to* kilograms
    private val toKilogram = mapOf(
        "Kilogram"   to 1.0,
        "Gram"       to 0.001,
        "Milligram"  to 1e-6,
        "Metric Ton" to 1_000.0,
        "Pound"      to 0.45359237,
        "Ounce"      to 0.0283495231,
        "Carat"      to 0.0002
    )

    /** Convert [value] in [fromUnit] to [toUnit]. */
    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        val inKg = value * (toKilogram[fromUnit] ?: error("Unknown unit $fromUnit"))
        return inKg / (toKilogram[toUnit] ?: error("Unknown unit $toUnit"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Weight",
        onNextClick = { navController.popBackStack() },
        content =
    {
        var fromText         by remember { mutableStateOf("") }
        var selectedFromUnit by remember { mutableStateOf("Kilogram") }
        var selectedToUnit   by remember { mutableStateOf("Gram") }

        val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
            derivedStateOf {
                fromText.toDoubleOrNull()
                    ?.let { WeightConversionUtils.convert(it, selectedFromUnit, selectedToUnit) }
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
                text = "Weight",
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
                        val sym = WeightConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                        Text("From ($sym)", color = Color.White)
                    },
                    placeholder = { Text("e.g. 5.0", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = resultText,
                    onValueChange = { /* no-op */ },
                    label = {
                        val sym = WeightConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
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
                    items(WeightConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedFromUnit
                        ) { selectedFromUnit = unit }
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(WeightConversionUtils.units) { unit ->
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