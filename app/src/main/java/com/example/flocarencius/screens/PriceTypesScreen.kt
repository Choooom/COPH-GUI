package com.example.flocarencius.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flocarencius.R
import com.example.flocarencius.components.GridMenuButton
import com.example.flocarencius.components.ScreenContainer
import com.example.flocarencius.ui.theme.Inria

@Composable
fun PriceTypesScreen(navController: NavController) {
    ScreenContainer(
        title = "Типы цен",
        onNextClick = { navController.popBackStack() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Kinematics",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = Inria,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.kinematics_equations),
                    contentDescription = "Home",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(270.dp)
                )

                Text(
                    text = "Choose from the \n8 formulas:",
                    fontFamily = Inria,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp),
                    lineHeight = 25.sp
                )
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "1",
                        onClick = { navController.navigate("k_one") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "5",
                        onClick = { navController.navigate("k_five") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "2",
                        onClick = { navController.navigate("k_two") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "6",
                        onClick = { navController.navigate("k_six") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "3",
                        onClick = { navController.navigate("k_three") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "7",
                        onClick = { navController.navigate("k_seven") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GridMenuButton(
                        text = "4",
                        onClick = { navController.navigate("k_four") },
                        modifier = Modifier.weight(1f)
                    )

                    GridMenuButton(
                        text = "8",
                        onClick = { navController.navigate("k_eight") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            }
        }
    )
}