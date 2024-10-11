package com.example.myapplication.model

class Character(var attributes: MutableMap<String, Int>, val race: Race) {
    fun applyRaceBonuses() {
        race.bonuses.forEach { (attr, bonus) ->
            attributes[attr] = attributes[attr]!! + bonus
        }
    }

    fun distributePoints(points: Int) {
        var remainingPoints = points
        while (remainingPoints > 0) {
            println("Você tem $remainingPoints pontos sobrando.")
            println("Atributos:")
            attributes.forEach { (attr, value) ->
                println("$attr: $value")
            }

            println("Informe o atributo que deseja aumentar:")
            val attribute = readLine()?.capitalize() ?: ""
            if (!attributes.containsKey(attribute)) {
                println("Atributo não existe.")
                continue
            }

            println("Quantos pontos para o atributo $attribute?")
            val inputPoints = readLine()?.toIntOrNull()

            if (inputPoints == null || inputPoints <= 0) {
                println("Input inválido.")
                continue
            }

            if (remainingPoints >= inputPoints && (attributes[attribute]!! + inputPoints) <= 15) {
                attributes[attribute] = attributes[attribute]!! + inputPoints
                remainingPoints -= inputPoints
            } else {
                println("Sem pontos suficientes ou limte (15) alcançado.")
            }
        }
    }

    fun calculateHealthPoints(): Int {
        val constitution = attributes["Constituição"] ?: 0
        val constitutionModifier = (constitution - 10) / 2
        return 10 + constitutionModifier
    }
}