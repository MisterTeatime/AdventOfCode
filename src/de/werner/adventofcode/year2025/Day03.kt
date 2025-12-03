package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day03 {
    private val testInput = readInput("""2025\Day03_test""")
    private val input = readInput("""2025\Day03""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        input
            .map { findMaxNumber(it, 2) }
            .sumOf { it.toLong() }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        input
            .map { findMaxNumber(it, 12) }
            .sumOf { it.toLong() }
    }

    fun testPart2() = solvePart2(testInput)

    fun findMaxNumber(input: String, digits: Int, start: Int = 0): String {
        if (digits == 0) return ""

        // valid interval: start to input.length - digits
        val end = input.length - digits
        if (start > end) return ""

        // Find largest digit with index IN VALID INTERVAL (start..end)
        val (maxPosition, maxDigit) = input.substring(start, end + 1)
            .withIndex()
            .maxBy { it.value }
            .let { (relPos, char) -> Pair(start + relPos, char) }

        // Rekursion for the rest starting at  maxPosition + 1
        return maxDigit + findMaxNumber(input, digits - 1, maxPosition + 1)
    }
}

