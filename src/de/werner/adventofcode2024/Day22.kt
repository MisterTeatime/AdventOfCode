package de.werner.adventofcode2024

import readInput

class Day22 {
    private val testInput = readInput("""2024\Day22_test""")
    private val input = readInput("""2024\Day22""")

    fun solvePart1(input: List<String> = this.input): Long {
        val startNumbers = input.map { it.toLong() }
        val sum = sumOfNthSecretNumber(startNumbers, 2000)

        return sum
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long {
        val startNumbers = input.map { it.toLong() }
        val diffPrices = mutableMapOf<List<Long>, Long>()
//        findBuyerPrices(123L, diffPrices)
//        findBuyerPrices(1L, diffPrices)
        startNumbers.forEach { findBuyerPrices(it, diffPrices) }

        return diffPrices.values.max()
    }

    fun testPart2() = solvePart2(testInput)

    private fun generateSecretNumberSequence(startNumber: Long): Sequence<Long> = sequence {
        var currentNumber = startNumber

        while (true) {
            currentNumber = calculateNextSecretNumber(currentNumber)
            yield(currentNumber)
        }
    }

    private fun calculateNextSecretNumber(startNumber: Long): Long {
        var currentNumber = startNumber

        currentNumber = ((currentNumber shl 6) xor currentNumber) and 0xFFFFFFL
        currentNumber = ((currentNumber shr 5) xor currentNumber) and 0xFFFFFFL
        currentNumber = ((currentNumber shl 11) xor currentNumber) and 0xFFFFFFL
        return currentNumber
    }

    private fun sumOfNthSecretNumber(startNumbers: List<Long>, n: Int): Long {
        var sum = 0L
        for (startNumber in startNumbers) {
            sum += generateSecretNumberSequence(startNumber).elementAt(n - 1)
        }
        return sum
    }

    private fun findBuyerPrices(startNumber: Long, diffPrices: MutableMap<List<Long>, Long>) {
        val visited = mutableSetOf<List<Long>>()
        val secretNumbers = generateSecretNumberSequence(startNumber).iterator()
        var currentPrice = startNumber % 10
        val currentPriceSequence = mutableListOf(currentPrice)

        repeat(2000) {
            currentPrice = secretNumbers.next() % 10
            currentPriceSequence.add(currentPrice)
            if (currentPriceSequence.size == 5) {
                val currentDiffs = currentPriceSequence.zipWithNext { a, b -> b - a }
                if (visited.add(currentDiffs)) {
                    diffPrices[currentDiffs] = (diffPrices[currentDiffs] ?: 0L) + currentPrice
                }
                currentPriceSequence.removeFirst()
            }
        }
    }
}

