package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day11 {
    private val testInput = readInput("""2025\Day11_test""")
    private val testInput2 = readInput("""2025\Day11_test02""")
    private val input = readInput("""2025\Day11""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        val counter = PathCounter(input)
        counter.countPathsMemo("you", "out")
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val counter = PathCounter(input, required = setOf("fft", "dac"))
        counter.countPathsMemo("svr", "out")
    }

    fun testPart2() = solvePart2(testInput2)
}

class PathCounter(private val input: List<String>,
                  private val required: Set<String> = emptySet()) {
    private val graph: Map<String, List<String>> = parseGraph()
    private val memo = mutableMapOf<Pair<String, Set<String>>, Long>()
    private val visited = mutableSetOf<String>()

    private fun parseGraph(): Map<String, List<String>> {
        return input.associate { line ->
            val (src, targets) = line.split(":", limit = 2)
            src.trim() to targets.trim().split(" ").filter { it.isNotBlank() }
        }
    }

    fun countPathsMemo(current: String, target: String): Long {
        if (current == target) {
            return if (required.all { it in visited }) 1L else 0L
        }
        if (current in visited) return 0L

        val visitedRequired = required.intersect(visited.toSet())
        val key = current to visitedRequired
        if (key in memo) return memo[key]!!

        visited.add(current)
        val result = graph[current]?.sumOf { countPathsMemo(it, target) } ?: 0L
        visited.remove(current)
        memo[key] = result
        return result
    }
}

