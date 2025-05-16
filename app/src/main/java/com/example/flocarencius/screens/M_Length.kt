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

object LengthConversionUtils {

    // Supported units
    val units = listOf(
        "Meter",
        "Kilometer",
        "Centimeter",
        "Millimeter",
        "Micrometer",
        "Yard",
        "Mile",
        "Foot",
        "Inch"
    )

    val symbols = mapOf(
        "Meter"      to "m",
        "Kilometer"  to "km",
        "Centimeter" to "cm",
        "Millimeter" to "mm",
        "Micrometer" to "μm",
        "Yard"       to "yd",
        "Mile"       to "mi",
        "Foot"       to "ft",
        "Inch"       to "in"
    )

    private val toMeterFactor = mapOf(
        "Meter"      to 1.0,
        "Kilometer"  to 1_000.0,
        "Centimeter" to 0.01,
        "Millimeter" to 0.001,
        "Micrometer" to 1e-6,
        "Yard"       to 0.9144,
        "Mile"       to 1_609.344,
        "Foot"       to 0.3048,
        "Inch"       to 0.0254
    )

    fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        val inMeters = value * (toMeterFactor[fromUnit] ?: error("Unknown unit $fromUnit"))
        return inMeters / (toMeterFactor[toUnit] ?: error("Unknown unit $toUnit"))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LengthConverterScreen(navController: NavController) {
    ScreenContainer(
        title = "Номенклатура",
        onNextClick = { navController.popBackStack() },
        content = {
            var fromText by remember { mutableStateOf("") }
            var selectedFromUnit by remember { mutableStateOf("Meter") }
            var selectedToUnit by remember { mutableStateOf("Kilometer") }

            // 2) Compute result whenever anything changes
            val resultText by remember(fromText, selectedFromUnit, selectedToUnit) {
                derivedStateOf {
                    fromText.toDoubleOrNull()
                        ?.let {
                            LengthConversionUtils.convert(
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
                Text(
                    text = "Length",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = Inria,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 40.dp, top = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // From field (editable)
                    OutlinedTextField(
                        value = fromText,
                        onValueChange = { fromText = it },
                        label = {
                            val symbol =
                                LengthConversionUtils.symbols[selectedFromUnit] ?: selectedFromUnit
                            Text("From ($symbol)", color = Color.White)
                        },
                        placeholder = { Text("e.g. 123", color = Color.White.copy(alpha = 0.8f)) },
                        modifier = Modifier.weight(1f),
                    )

                    OutlinedTextField(
                        value = resultText,
                        onValueChange = { },
                        label = {
                            val symbol =
                                LengthConversionUtils.symbols[selectedToUnit] ?: selectedToUnit
                            Text("To ($symbol)", color = Color.White)
                        },
                        readOnly = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(1f),               // take remaining vertical space
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(LengthConversionUtils.units) { unit ->
                            UnitRow(
                                unit = unit,
                                isSelected = unit == selectedFromUnit,
                                onClick = { selectedFromUnit = unit }
                            )
                        }
                    }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(LengthConversionUtils.units) { unit ->
                            UnitRow(
                                unit = unit,
                                isSelected = unit == selectedToUnit,
                                onClick = { selectedToUnit = unit }
                            )
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
    val bg = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    else Color.Transparent

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
