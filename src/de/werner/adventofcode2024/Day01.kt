package de.werner.adventofcode2024

import kotlin.math.abs

class Day01 {
    fun solvePart1(input: List<String>): Int {
        val (firstList, secondList) = getLists(input)

        firstList.sort()
        secondList.sort()

        val combined = firstList.zip(secondList) { a, b -> abs(a - b) }
        return combined.sum()
    }

    fun solvePart2(input: List<String>): Int {
        val (firstList, secondList) = getLists(input)

        val result = firstList
            .map { it * secondList.count { elem -> elem == it } }

        return result.sum()
    }

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

