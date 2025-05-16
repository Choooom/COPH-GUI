package com.example.flocarencius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flocarencius.screens.*
import com.example.flocarencius.ui.theme.COPHGUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COPHGUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("nomenclature") {
            NomenclatureScreen(navController = navController)
        }
        composable("counterparties") {
            CounterpartiesScreen(navController = navController)
        }
        composable("price_types") {
            PriceTypesScreen(navController = navController)
        }
        composable("warehouses") {
            WarehousesScreen(navController = navController)
        }
        composable("price_list") {
            PriceListScreen(navController = navController)
        }
        composable("work") {
            WorkScreen(navController = navController)
        }
        composable("operation_types") {
            OperationTypesScreen(navController = navController)
        }
        composable("document_types") {
            DocumentTypesScreen(navController = navController)
        }
        composable("reports") {
            ReportsScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("m_length") {
            LengthConverterScreen(navController = navController)
        }
        composable("m_volume") {
            VolumeConverterScreen(navController = navController)
        }
        composable("m_temperature") {
            TemperatureConverterScreen(navController = navController)
        }
        composable("m_weight") {
            WeightConverterScreen(navController = navController)
        }
        composable("m_area") {
            AreaConverterScreen(navController = navController)
        }
        composable("m_time") {
            TimeConverterScreen(navController = navController)
        }
    }
}
