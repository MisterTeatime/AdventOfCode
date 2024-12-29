package de.werner.adventofcode2024

import readInput

class Day03 {
    private val testInput = readInput("""2024\Day03_test""")
    private val input = readInput("""2024\Day03""")

    fun solvePart1(input: List<String> = this.input): Int {

        val mults = input.map { extractMultiplications(it) }.flatten()

        return mults.sumOf { (x, y) -> x * y }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int {
        val mults = processInstructions(input.joinToString(""))

        return mults.sumOf { (a,b) -> a*b }
    }

    fun testPart2() = solvePart2(testInput)

    private fun extractMultiplications(input: String): List<Pair<Int, Int>> {
        // Regex für die Multiplikationsanweisung: mul(X,Y) mit 1-3-stelligen Zahlen
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

        // Finden aller Matches
        return regex.findAll(input).map { matchResult ->
            // Extrahieren der Gruppen 1 und 2 (X und Y) und Konvertierung zu Int
            val x = matchResult.groupValues[1].toInt()
            val y = matchResult.groupValues[2].toInt()
            Pair(x, y) // Rückgabe als Tupel
        }.toList() // Umwandeln in eine Liste
    }

    private fun processInstructions(input: String): List<Pair<Int, Int>> {
        val regex = Regex("""(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""")
        val matches = regex.findAll(input)

        var isEnabled = true
        val result = mutableListOf<Pair<Int,Int>>()

        for (match in matches) {
            val token = match.value

            when {
                token.startsWith("do()") -> isEnabled = true
                token.startsWith("don't()") -> isEnabled = false
                token.startsWith("mul(") && isEnabled -> {
                    val numbers = Regex("""\d{1,3}""")
                        .findAll(token)
                        .map {it.value.toInt() }
                        .toList()
                    result.add(Pair(numbers[0], numbers[1]))
                }
            }
        }
        return result
    }
}

