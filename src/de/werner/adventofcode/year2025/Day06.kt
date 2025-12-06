package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day06 {
    private val testInput = readInput("""2025\Day06_test""")
    private val input = readInput("""2025\Day06""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        val opLine = input.last().trim()
        val dataLines = input.dropLast(1)

        val numbers: List<List<Long>> = dataLines
            .map { line ->
                line.trim()
                    .split(Regex("\\s+"))
                    .map { it.toLong() }
            }

        val operators: List<Char> = opLine.filter { !it.isWhitespace() }.toList()

        var sum = 0L
        for (col in operators.indices) {
            val colValues = numbers.map { it[col] }
            val colResult = when (operators[col]) {
                '+' -> colValues.fold(0L) { acc, l -> acc + l }
                '*' -> colValues.fold(1L) { acc, l -> acc * l }
                else -> error("Unknown operator ${operators[col]}")
            }
            sum += colResult
        }
        sum
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val height = input.size
        val width = input.maxOf { it.length }

        val operations = mapOf<Char, (List<Long>) -> Long>(
            '+' to { it.fold(0L) { acc, v -> acc + v } },
            '*' to { it.fold(1L) { acc, v -> acc * v } }
        )

        val numbers = mutableListOf<Long>()
        var total = 0L

        for (col in width  downTo 0) {
            val columnString = (0 until height).mapNotNull { row ->
                if (col < input[row].length) input[row][col] else null
            }.joinToString("").trim()

            if (columnString.isNotEmpty()) {
                if (columnString.last() in listOf('+', '*')) {
                    val operator = columnString.last()
                    val numberString = columnString.dropLast(1).trim()
                    if (numberString.isNotEmpty()) {
                        numbers.add(numberString.toLong())
                    }

                    total += operations[operator]!!(numbers)
                    numbers.clear()
                } else {
                    numbers.add(columnString.toLong())
                }
            }
        }
        total
    }

    fun testPart2() = solvePart2(testInput)

}

