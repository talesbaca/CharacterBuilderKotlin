package com.example.myapplication.creator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.persistence.AppDatabase
import com.example.myapplication.persistence.CharacterEntity
import kotlinx.coroutines.launch

@Composable
fun CharacterListScreen(navController: NavController, database: AppDatabase) {
    val coroutineScope = rememberCoroutineScope()
    var characters by remember { mutableStateOf<List<CharacterEntity>>(emptyList()) }
    var isEditDialogOpen by remember { mutableStateOf(false) }
    var characterToEdit by remember { mutableStateOf<CharacterEntity?>(null) }
    var updatedName by remember { mutableStateOf(TextFieldValue()) }

    fun loadCharacters() {
        coroutineScope.launch {
            characters = database.characterDao().getAllCharacters()
        }
    }

    fun deleteCharacter(character: CharacterEntity) {
        coroutineScope.launch {
            database.characterDao().delete(character)
            loadCharacters()
        }
    }

    fun updateCharacterName(newName: String) {
        coroutineScope.launch {
            characterToEdit?.let {
                val updatedCharacter = it.copy(name = newName)
                database.characterDao().update(updatedCharacter)
                loadCharacters()
            }
        }
    }

    LaunchedEffect(Unit) {
        loadCharacters()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(onClick = {
            navController.navigate("name_screen")
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Personagens Criados",
            style = MaterialTheme.typography.headlineLarge,
            color = Color(0xFF4A148C),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (characters.isNotEmpty()) {
            characters.forEach { character ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nome: ${character.name}", fontSize = 18.sp, color = Color(0xFF4A148C))
                        Text(text = "Raça: ${character.race}", fontSize = 16.sp, color = Color.Black)
                        Text(text = "Pontos de Vida: ${character.hitPoints}", fontSize = 14.sp, color = Color.Gray)

                        // Display character attributes
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Força: ${character.strength}", fontSize = 14.sp)
                        Text(text = "Destreza: ${character.dexterity}", fontSize = 14.sp)
                        Text(text = "Constituição: ${character.constitution}", fontSize = 14.sp)
                        Text(text = "Inteligência: ${character.intelligence}", fontSize = 14.sp)
                        Text(text = "Sabedoria: ${character.wisdom}", fontSize = 14.sp)
                        Text(text = "Carisma: ${character.charisma}", fontSize = 14.sp)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            // Edit button
                            IconButton(onClick = {
                                characterToEdit = character
                                updatedName = TextFieldValue(character.name)
                                isEditDialogOpen = true
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Character", tint = Color.Blue)
                            }

                            // Delete button
                            IconButton(onClick = { deleteCharacter(character) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Character", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Nenhum personagem foi criado ainda.",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }

    // Edit character dialog
    if (isEditDialogOpen && characterToEdit != null) {
        AlertDialog(
            onDismissRequest = { isEditDialogOpen = false },
            title = { Text("Editar Nome do Personagem") },
            text = {
                TextField(
                    value = updatedName,
                    onValueChange = { updatedName = it },
                    label = { Text("Novo Nome") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        updateCharacterName(updatedName.text)
                        isEditDialogOpen = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isEditDialogOpen = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
