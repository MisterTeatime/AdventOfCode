package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day07 {
    private val testInput = readInput("""2025\Day07_test""")
    private val input = readInput("""2025\Day07""")

    fun solvePart1(input: List<String> = this.input): Int = timing {
        val rays = mutableSetOf<Int>()
        var splits = 0
        rays.add(getStartColumn(input[0]))

        for (line in input.indices) {
            val row = input[line]
            val newRays = mutableSetOf<Int>()
            val toRemove = mutableSetOf<Int>()

            for (c in rays.toList()) {
                if (c < row.length && row[c] == '^') {
                    splits++
                    toRemove.add(c)
                    if (c - 1 >= 0) newRays.add(c - 1)
                    if (c + 1 < row.length) newRays.add(c + 1)
                }
            }
            rays.removeAll(toRemove)
            rays.addAll(newRays)
        }
        splits
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val startCol = getStartColumn(input[0])
        var rayCount = mutableMapOf<Int, Long>()

        rayCount[startCol] = 1L

        for (line in input.indices) {
            val row = input[line]
            val newRays = mutableMapOf<Int, Long>()

            for ((c, count) in rayCount) {
                if (c < row.length && row[c] == '^') {
                    if (c - 1 >= 0) newRays[c - 1] = newRays.getOrDefault(c - 1, 0L) + count
                    if (c + 1 < row.length) newRays[c + 1] = newRays.getOrDefault(c + 1, 0L) + count
                } else {
                    newRays[c] = newRays.getOrDefault(c, 0L) + count
                }
            }

            rayCount = newRays
        }

        rayCount.values.sum()
    }

    fun testPart2() = solvePart2(testInput)

    fun getStartColumn(input: String): Int = input.indexOf('S')
}

