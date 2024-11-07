package com.example.myapplication.creator

import com.example.myapplication.model.Character
import com.example.myapplication.model.Race

interface CharacterCreator {
    fun createCharacter(race: Race, points: Any): Character
}