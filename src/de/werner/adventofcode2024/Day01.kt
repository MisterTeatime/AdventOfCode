package de.werner.adventofcode2024

import readInput
import kotlin.math.abs

class Day01 {
    private val testInput = readInput("Day01_test")
    private val input = readInput("Day01")

    fun solvePart1(input: List<String> = this.input): Int {
        val (firstList, secondList) = getLists(input)

        firstList.sort()
        secondList.sort()

        val combined = firstList.zip(secondList) { a, b -> abs(a - b) }
        return combined.sum()
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int {
        val (firstList, secondList) = getLists(input)

        val result = firstList
            .map { it * secondList.count { elem -> elem == it } }

        return result.sum()
    }

    fun testPart2() = solvePart2(testInput)

    private fun getLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        for (line in input) {
            val (first, second) = line
                .split("   ")
                .map { it.toInt() }

            firstList.add(first)
            secondList.add(second)
        }

        return firstList to secondList
    }
}

