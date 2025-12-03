package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day02 {
    private val testInput = readInput("""2025\Day02_test""")
    private val input = readInput("""2025\Day02""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        getLongIntervals(input[0])
            .sumOf { range -> range
                .filter { isInvalidDoubleId(it) }
                .sum()
            }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        getLongIntervals(input[0])
            .sumOf { range -> range
                .filter { isInvalidId(it) }
                .sum()
            }
    }

    fun testPart2() = solvePart2(testInput)

    fun getLongIntervals(input: String) = input
        .splitToSequence(',')
        .map { range ->
            val (start, end) = range.split('-')
            start.toLong()..end.toLong()
        }

    fun isInvalidId(n: Long): Boolean {
        val s = n.toString()

        if (s.isEmpty()) return false

        val regex = """^(.*)\1+$""".toRegex()
        return regex.matches(s)
    }

    fun isInvalidDoubleId(n: Long): Boolean {
        val s = n.toString()

        if (s.length % 2 != 0) return false

        val center = s.length / 2
        return s.take(center) == s.drop(center)
    }
}

