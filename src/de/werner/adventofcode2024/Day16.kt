package de.werner.adventofcode2024

import Extensions.*
import Point2D
import readInput

class Day16 {
    private val testInput = readInput("Day16_test")
    private val input = readInput("Day16")

    fun solvePart1(input: List<String> = this.input): Int {

        val (start, end) = input.findStartEnd()
        if (start == null || end == null) throw IllegalArgumentException("Start oder Ende nicht gefunden")

        return input.size
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = input.size

    fun testPart2() = solvePart2(testInput)

    private fun List<String>.findStartEnd(): Pair<Point2D?, Point2D?> {
        var start: Point2D? = null
        var end: Point2D? = null

        for (y in this.indices) {
            for (x in this[y].indices) {
                when (this[y][x]) {
                    'S' -> start = Point2D(x,y)
                    'E' -> end = Point2D(x,y)
                }
            }
        }
        return (start to end)
    }

    private fun Point2D.walkFrom(map: List<String>): Sequence<Point2D> = sequence{
        val stack = ArrayDeque<Point2D>()
        val visited = mutableSetOf<Point2D>()

        stack.push(this@walkFrom)

        while (stack.size > 0) {
            val current = stack.pop()

            if(!visited.add(current))
                yield(current)


        }
    }
}

