package com.example.composenavigationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationapp.ui.theme.ComposeNavigationAppTheme

const val ROTA_SCREEN_A = "screen_a"
const val ROTA_SCREEN_B = "screen_b"
const val ROTA_SCREEN_C = "screen_c"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationAppTheme {
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
    
    NavHost(navController = navController, startDestination = ROTA_SCREEN_A) {
        
        composable(ROTA_SCREEN_A) {
            ScreenA(navController = navController)
        }
        
        // Rota B configurada para receber o argumento 'contador' (Exercício 2)
        composable("$ROTA_SCREEN_B?contador={contador}") { backStackEntry ->
            // Recupera e converte para Int
            val contadorString = backStackEntry.arguments?.getString("contador")
            val contador = contadorString?.toIntOrNull() ?: 0
            
            ScreenB(navController = navController, contador = contador)
        }
        
        composable(ROTA_SCREEN_C) {
            ScreenC(navController = navController)
        }
    }
}


// ==========================================
// Telas (Composables)
// ==========================================

@Composable
fun ScreenA(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta é a Tela A", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { navController.navigate("$ROTA_SCREEN_B?contador=10") }) {
            Text("Ir para Tela B com Contador (10)")
        }
        Button(onClick = { navController.navigate(ROTA_SCREEN_B) }) {
            Text("Ir para Tela B (sem Contador)")
        }
    }
}

@Composable
fun ScreenB(navController: NavController, contador: Int) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta é a Tela B", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = "Valor do Contador: $contador", color = MaterialTheme.colorScheme.primary)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Navega para ScreenC (Exercício 1)
        Button(onClick = { navController.navigate(ROTA_SCREEN_C) }) {
            Text("Avançar para Tela C")
        }
        
        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar para Tela A")
        }
    }
}

@Composable
fun ScreenC(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta é a Tela C", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar para Tela B")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(onClick = {
            navController.navigate(ROTA_SCREEN_A) {
                popUpTo(ROTA_SCREEN_A) { inclusive = false } 
            } 
        }) {
            Text("Voltar ao Início (Tela A)")
        }
    }
}