package com.example.myapplication.creator

import com.example.myapplication.model.Character
import com.example.myapplication.model.Race

class DefaultCharacterCreator : CharacterCreator {
    override fun createCharacter(race: Race, points: Any): Character {
        val attributes = mutableMapOf(
            "Força" to 8,
            "Destreza" to 8,
            "Constituição" to 8,
            "Inteligência" to 8,
            "Sabedoria" to 8,
            "Carisma" to 8
        )

        val character = Character(attributes, race)

        println("Pontos distribuidos! Vamos aplicar o seu bônus de raça....")
        character.applyRaceBonuses()

        return character
    }
}