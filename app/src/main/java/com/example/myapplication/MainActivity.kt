package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.model.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterCreationScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCreationScreen(modifier: Modifier = Modifier) {
    var strength by remember { mutableStateOf("") }
    var dexterity by remember { mutableStateOf("") }
    var constitution by remember { mutableStateOf("") }
    var intelligence by remember { mutableStateOf("") }
    var wisdom by remember { mutableStateOf("") }
    var charisma by remember { mutableStateOf("") }
    var selectedRace by remember { mutableStateOf("Humano") }
    var hitPoints by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    val races = listOf("Humano", "Elfo", "Anão", "Halfling", "Gnomo", "Draconato")

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = strength,
            onValueChange = { strength = it },
            label = { Text("Força") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = dexterity,
            onValueChange = { dexterity = it },
            label = { Text("Destreza") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = constitution,
            onValueChange = { constitution = it },
            label = { Text("Constituição") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = intelligence,
            onValueChange = { intelligence = it },
            label = { Text("Inteligência") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = wisdom,
            onValueChange = { wisdom = it },
            label = { Text("Sabedoria") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = charisma,
            onValueChange = { charisma = it },
            label = { Text("Carisma") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedRace,
                onValueChange = {},
                readOnly = true,
                label = { Text("Raça") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                races.forEach { race ->
                    DropdownMenuItem(
                        text = { Text(text = race) },
                        onClick = {
                            selectedRace = race
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                // Validação e cálculo dos pontos de vida
                val valid = validateAttributes(strength, dexterity, constitution, intelligence, wisdom, charisma)
                if (valid) {
                    val race = getRace(selectedRace)
                    val character = createCharacter(race, strength.toInt(), dexterity.toInt(), constitution.toInt(), intelligence.toInt(), wisdom.toInt(), charisma.toInt())
                    hitPoints = character.calculateHealthPoints()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text("Calcular Pontos de Vida")
        }

        Text(text = "Pontos de Vida: $hitPoints", style = MaterialTheme.typography.bodyLarge)
    }
}

fun validateAttributes(strength: String, dexterity: String, constitution: String, intelligence: String, wisdom: String, charisma: String): Boolean {
    // Validação dos atributos
    val attributes = listOf(strength, dexterity, constitution, intelligence, wisdom, charisma)
    if (attributes.any { it.toIntOrNull() == null || it.toInt() !in 8..15 }) {
        return false
    }
    return attributes.sumOf { it.toInt() } == 27
}

fun getRace(raceName: String): Race {
    return when (raceName) {
        "Humano" -> Human()
        "Elfo" -> Elf()
        "Anão" -> Dwarf()
        "Halfling" -> Halfling()
        "Gnomo" -> Gnome()
        "Draconato" -> DragonBorn()
        else -> Human()
    }
}

fun createCharacter(race: Race, strength: Int, dexterity: Int, constitution: Int, intelligence: Int, wisdom: Int, charisma: Int): Character {
    val attributes = mutableMapOf(
        "Força" to strength,
        "Destreza" to dexterity,
        "Constituição" to constitution,
        "Inteligência" to intelligence,
        "Sabedoria" to wisdom,
        "Carisma" to charisma
    )
    val character = Character(attributes, race)
    character.applyRaceBonuses()
    return character
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        CharacterCreationScreen()
    }
}