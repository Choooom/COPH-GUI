package com.example.flocarencius.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flocarencius.R
import com.example.flocarencius.ui.theme.DarkGreen
import com.example.flocarencius.ui.theme.Inria

@Composable
fun AppHeader(title: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(
            topStart     = 0.dp,
            topEnd       = 0.dp,
            bottomStart  = 30.dp,
            bottomEnd    = 30.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Icon Section (20%)
                    Box(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.olfu_logo),
                                contentDescription = "Home",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(65.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(bottom = 15.dp)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Our Lady of",
                            fontSize = 28.sp,
                            fontFamily = Inria,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF1F1F1),
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Fatima University",
                            fontSize = 28.sp,
                            fontFamily = Inria,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF1F1F1),
                            style = MaterialTheme.typography.labelLarge.copy(lineHeight = 30.sp),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Right Icon Section (20%)
                    Box(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.fcms_logo),
                                contentDescription = "Menu",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(55.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    textColor: Color = Color(0xFF001909) // Default text color
) {
    Button(
        onClick = onClick,
        border = BorderStroke(2.dp, Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor, // Explicitly set text color
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 7.dp)
        )
    }
}


@Composable
fun GridMenuButton(
    text: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 16.sp,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(2.dp, Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .height(60.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = DarkGreen,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth(0.5f)
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = text,
            color = DarkGreen,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ScreenContainer(
    title: String = "Our Lady of Fatima University",
    content: @Composable () -> Unit,
    onNextClick: () -> Unit,
    mema: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppHeader(title = title)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            content()
        }

        if (mema == 0){
            PrimaryButton(
                text = "Back",
                onClick = onNextClick
            )
        }
    }
}
