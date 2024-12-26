package de.werner.adventofcode2024

import readInput
import kotlin.math.abs

class Day02 {
    private val testInput = readInput("Day02_test")
    private val input = readInput("Day02")

    fun solvePart1(input: List<String> = this.input): Int {

        val diffs = input.map {
                line -> line
            .split(" ")
            .map { it.toInt() }
            .zipWithNext { a, b -> b - a }
        }.map { when {
            !it.all { el -> abs(el) in 1..3 } -> false
            it.all { el -> el > 0 } || it.all { el -> el < 0 } -> true
            else -> false
        }
        }

        return diffs.count { it }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int {
        val diffs = input.map { line ->
            val numbers = line.split(" ").map { it.toInt() }

            // Prüft, ob die Sequenz ohne Änderungen gültig ist
            fun isValid(sequence: List<Int>): Boolean {
                val differences = sequence.zipWithNext { a, b -> b - a }
                return differences.all { abs(it) in 1..3 } &&
                        (differences.all { it > 0 } || differences.all { it < 0 })
            }

            // Prüft die ursprüngliche Sequenz und alle möglichen Sequenzen mit einem entfernten Element
            if (isValid(numbers)) {
                true
            } else {
                numbers.indices.any { index ->
                    val reducedSequence = numbers.toMutableList().apply { removeAt(index) }
                    isValid(reducedSequence)
                }
            }
        }
        return diffs.count {it}
    }

    fun testPart2() = solvePart2(testInput)
}

