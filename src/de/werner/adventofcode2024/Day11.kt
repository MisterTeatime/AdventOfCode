package de.werner.adventofcode2024

import readInput
import kotlin.math.pow

class Day11 {
    private val testInput = readInput("Day11_test")
    private val input = readInput("Day11")

    fun solvePart1(input: List<String> = this.input): Int {
        var result = input[0].split(" ").map { it.toLong() }

        repeat(25) {
            result = processListParallel(result)
        }

        return result.size
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long {
        var result = input[0]
            .split(" ")
            .groupingBy { it.toLong() }
            .eachCount()
            .mapValues { it.value.toLong() }

        repeat(75) {
            result = processIterationWithCount(result)
        }

        return result.values.sum()
    }

    fun testPart2() = solvePart2(testInput)

    private fun applyRules(input: List<Long>): List<Long> {
        require(input.size == 1) {"Input Liste muss genau ein Element enthalten."}
        val number = input.first()
        val cache = mutableMapOf<Long, List<Long>>()

        return when {
            //Regel 1: 0 wird 1
            number == 0L -> cache.computeIfAbsent(number) { listOf(1L) }

            //Regel 2: Gerade Stellenanzahl wird aufgeteilt
            number.toString().length % 2 == 0 -> cache.computeIfAbsent(number) { applyRule2(number) }

            //Regel 3: Zahl mit 2024 multiplizieren
            else -> cache.computeIfAbsent(number) { listOf(number * 2024) }
        }
    }

    private fun processListParallel(input: List<Long>): List<Long> = input.parallelStream()
        .flatMap { applyRules(listOf(it)).stream() }
        .toList()

    private fun processIterationWithCount(input: Map<Long, Long>): Map<Long, Long> {
        val result = mutableMapOf<Long, Long>()

        for ((value, count) in input) {
            val transformedValue = applyRules(listOf(value))

            for (newValue in transformedValue) {
                result[newValue] = count + result.getOrDefault(newValue, 0L)
            }
        }

        return result
    }

    private fun applyRule2(number: Long): List<Long> {
        val powerOfTen = 10.0.pow((number.toString().length / 2).toDouble()).toLong()
        val firstPart = number / powerOfTen
        val secondPart = number % powerOfTen
        return listOf(firstPart, secondPart)
    }
}

