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

object TimeConversionUtils {

    // 1) Supported units
    val units = listOf(
        "Second",
        "Millisecond",
        "Minute",
        "Hour",
        "Day",
        "Week",
        "Month",
        "Year"
    )

    // 2) Symbols for labels
    val symbols = mapOf(
        "Second"      to "s",
        "Millisecond" to "ms",
        "Minute"      to "min",
        "Hour"        to "h",
        "Day"         to "d",
        "Week"        to "wk",
        "Month"       to "mo",
        "Year"        to "yr"
    )

    // 3) Factors to convert *to* seconds
    private val toSeconds = mapOf(
        "Second"      to 1.0,
        "Millisecond" to 1e-3,
        "Minute"      to 60.0,
        "Hour"        to 3_600.0,
        "Day"         to 86_400.0,
        "Week"        to 604_800.0,
        // using average month = 30.44 days
        "Month"       to 2_629_743.83,
        // using Julian year = 365.25 days
        "Year"        to 31_557_600.0
    )

    /** Convert [value] in [fromUnit] to [toUnit]. */
    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        val inSecs = value * (toSeconds[fromUnit] ?: error("Unknown unit $fromUnit"))
        return inSecs / (toSeconds[toUnit] ?: error("Unknown unit $toUnit"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Time",
        onNextClick = { navController.popBackStack() },
        content =
    {
        var fromText         by remember { mutableStateOf("") }
        var selectedFromUnit by remember { mutableStateOf("Second") }
        var selectedToUnit   by remember { mutableStateOf("Minute") }

        val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
            derivedStateOf {
                fromText.toDoubleOrNull()
                    ?.let { TimeConversionUtils.convert(it, selectedFromUnit, selectedToUnit) }
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
                text = "Time",
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
                        val sym = TimeConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                        Text("From ($sym)", color = Color.White)
                    },
                    placeholder = { Text("e.g. 3600", color = Color.White.copy(alpha = 0.8f)) },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = resultText,
                    onValueChange = { /* no-op */ },
                    label = {
                        val sym = TimeConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
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
                    items(TimeConversionUtils.units) { unit ->
                        UnitRow(
                            unit = unit,
                            isSelected = unit == selectedFromUnit
                        ) { selectedFromUnit = unit }
                    }
                }
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(TimeConversionUtils.units) { unit ->
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