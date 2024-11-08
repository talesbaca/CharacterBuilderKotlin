package com.example.myapplication.creator

import com.example.myapplication.model.Character
import com.example.myapplication.model.Race

class DefaultCharacterCreator : CharacterCreator {
    override fun createCharacter(race: Race, points: List<String>): Character {
        val attributes = mutableMapOf(
            "Força" to (points.getOrElse(0) { "8" }.toIntOrNull() ?: 8),
            "Destreza" to (points.getOrElse(1) { "8" }.toIntOrNull() ?: 8),
            "Constituição" to (points.getOrElse(2) { "8" }.toIntOrNull() ?: 8),
            "Inteligência" to (points.getOrElse(3) { "8" }.toIntOrNull() ?: 8),
            "Sabedoria" to (points.getOrElse(4) { "8" }.toIntOrNull() ?: 8),
            "Carisma" to (points.getOrElse(5) { "8" }.toIntOrNull() ?: 8)
        )

        val character = Character(attributes, race)

        println("Pontos distribuídos! Vamos aplicar o seu bônus de raça....")
        character.applyRaceBonuses()

        return character
    }
}
