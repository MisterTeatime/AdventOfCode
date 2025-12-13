package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day12 {
    private val testInput = readInput("""2025\Day12_test""")
    private val input = readInput("""2025\Day12""")

    fun solvePart1(input: List<String> = this.input): Long = timing { input.size.toLong() }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing { input.size.toLong() }

    fun testPart2() = solvePart2(testInput)
}

