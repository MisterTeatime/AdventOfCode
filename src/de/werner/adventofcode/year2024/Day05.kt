package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*

class Day05 {
    private val testInput = readInput("""2024\Day05_test""")
    private val input = readInput("""2024\Day05""")

    fun solvePart1(input: List<String> = this.input): Int = timing {
        val (inst, upd) = splitList(input)

        val instructions = inst.map { instruction ->
            val (first, second) = instruction.split("|").map { it.toInt() }
            first to second
        }
        val updates = upd.map { it.split(",").map { el -> el.toInt() } }

        val validUpdates = updates.filter { isValidUpdate(instructions, it)}
        return@timing validUpdates.sumOf { it[it.size / 2] }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing {
        val (inst, upd) = splitList(input)

        val instructions = inst.map { instruction ->
            val (first, second) = instruction.split("|").map { it.toInt() }
            first to second
        }
        val updates = upd.map { it.split(",").map { el -> el.toInt() } }

        val correctedUpdates = updates.mapNotNull { update ->
            if (!isValidUpdate(instructions, update)) {
                correctUpdate(instructions, update)
            } else {
                null
            }
        }

        return@timing correctedUpdates.sumOf { it[it.size / 2] }
    }

    fun testPart2() = solvePart2(testInput)

    private fun splitList(input: List<String>): Pair<List<String>, List<String>> {
        val splitIndex = input.indexOf("")

        return if (splitIndex == -1)
            Pair(emptyList(), emptyList())
        else {
            val before = input.take(splitIndex)
            val after = input.drop(splitIndex + 1)
            Pair(before, after)
        }
    }

    private fun isValidUpdate(instructions: List<Pair<Int, Int>>, update: List<Int>): Boolean = instructions
        .filter { (first, second) -> update.contains(first) && update.contains(second) }
        .all { (before, after) ->
            val indexAfter = update.indexOf(after)
            val indexBefore = update.indexOf(before)
            indexBefore < indexAfter
        }

    private fun correctUpdate(instructions: List<Pair<Int, Int>>, update: List<Int>): List<Int> {
        val relevantInstructions = instructions.filter { (first, second) ->
            update.contains(first) && update.contains(second)
        }
        return update.sortedWith { a, b ->
            when {
                relevantInstructions.any { it.first == a && it.second == b } -> -1
                relevantInstructions.any { it.first == b && it.second == b } -> 1
                else -> 0
            }
        }
    }
}

