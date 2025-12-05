package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day05 {
    private val testInput = readInput("""2025\Day05_test""")
    private val input = readInput("""2025\Day05""")

    fun solvePart1(input: List<String> = this.input): Int = timing {
        val ranges = getFreshRanges(input)
        val items = getItems(input)

        items.count { item -> ranges.any { range -> item in range } }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        countUniqueIds(getFreshRanges(input))
    }

    fun testPart2() = solvePart2(testInput)

    fun getFreshRanges(input: List<String>): List<LongRange> =
        input
            .takeWhile { it.isNotEmpty() }
            .map {
                val (start, end) = it.split("-").map(String::toLong)
                start..end
            }

    fun getItems(input: List<String>): List<Long> =
        input
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .map { it.toLong() }

    fun countUniqueIds(ranges: List<LongRange>): Long {
        if (ranges.isEmpty()) return 0L

        val merged = ranges.sortedBy { it.first }.toMutableList()
        var i = 1

        while (i < merged.size) {
            val current = merged[i]
            val previous = merged[i - 1]

            if (current.first <= previous.last) {
                val newRange = previous.first..maxOf(previous.last, current.last)
                merged[i - 1] = newRange
                merged.removeAt(i)
            } else {
                i++
            }
        }
        return merged.sumOf { it.last - it.first + 1 }
    }
}

