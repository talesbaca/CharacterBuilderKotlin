package com.example.myapplication.creator

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.persistence.AppDatabase

@Composable
fun NavGraph(startDestination: String = "name_screen", database: AppDatabase) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("name_screen") {
            CharacterNameScreen(database = database) { characterName ->
                navController.navigate("creation_screen/$characterName")
            }
        }
        composable(
            route = "creation_screen/{characterName}",
            arguments = listOf(navArgument("characterName") { type = NavType.StringType })
        ) { backStackEntry ->
            val characterName = backStackEntry.arguments?.getString("characterName") ?: ""
            CharacterCreationScreen(database = database, characterName = characterName)
        }
    }
}