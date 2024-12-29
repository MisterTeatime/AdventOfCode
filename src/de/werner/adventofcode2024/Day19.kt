package de.werner.adventofcode2024

import readInput

class Day19 {
    private val testInput = readInput("""2024\Day19_test""")
    private val input = readInput("""2024\Day19""")

    fun solvePart1(input: List<String> = this.input): Int {
        val (words, sentences) = splitInput(input)

        val result = sentences.map { isValidSequence(it, words) }
        return result.count { it }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long {
        val (words, sentences) = splitInput(input)

        val result = sentences.map { countValidCombinations(it, words) }
        return result.sum()
    }

    fun testPart2() = solvePart2(testInput)

    private fun splitInput(input: List<String>): Pair<List<String>, List<String>> {
        val words = input[0].split(", ")
        val sentences = input.drop(2)

        return words to sentences
    }

    private fun isValidSequence(sentence: String, words: List<String>): Boolean {
        val wordSet = words.toHashSet()
        val memo = mutableMapOf<Int, Boolean>()

        fun canForm(start: Int): Boolean {
            if (start == sentence.length) return true

            if (memo.containsKey(start)) return memo[start]!!

            for (end in start + 1..sentence.length) {
                val word = sentence.substring(start, end)
                if (word in wordSet && canForm(end)) {
                    memo[start] = true
                    return true
                }
            }
            memo[start] = false
            return false
        }
        return canForm(0)
    }

    private fun countValidCombinations(sentence: String, words: List<String>): Long {
        val wordSet = words.toHashSet()
        val memo = mutableMapOf<String, Long>()

        fun countWays(startIndex: Int): Long {
            if (startIndex == sentence.length) return 1
            if (memo.containsKey(startIndex.toString())) return memo[startIndex.toString()]!!

            var totalWays = 0L

            for (word in wordSet) {
                if (sentence.startsWith(word, startIndex)) {
                    totalWays += countWays(startIndex + word.length)
                }
            }
            memo[startIndex.toString()] = totalWays
            return totalWays
        }
        return countWays(0)
    }
}

