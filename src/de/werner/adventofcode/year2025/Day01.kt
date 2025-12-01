package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day01 {
    private val testInput = readInput("""2025\Day01_test""")
    private val input = readInput("""2025\Day01""")

    fun solvePart1(input: List<String> = this.input): Int = timing { input.size }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing { input.size }

    fun testPart2() = solvePart2(testInput)
}

