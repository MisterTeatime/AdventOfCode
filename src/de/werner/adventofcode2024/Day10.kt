package de.werner.adventofcode2024

import Point2D
import readInput

class Day10 {
    private val testInput = readInput("""2024\Day10_test""")
    private val input = readInput("""2024\Day10""")

    fun solvePart1(input: List<String> = this.input): Int {
        val map = input.map { str -> str.map { char -> char.toString().toInt() }}
        val starts = map.findTrailStarts()
        val sum = starts.sumOf { trailhead -> trailhead.getScore(map) }

        return sum
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int {
        val map = input.map { str -> str.map { char -> char.toString().toInt() }}
        val starts = map.findTrailStarts()
        val sum = starts.sumOf { trailhead -> trailhead.getRating(map) }

        return sum
    }

    fun testPart2() = solvePart2(testInput)

    private fun List<List<Int>>.findTrailStarts(): MutableList<Point2D> {
        val map = this
        val trailheads = mutableListOf<Point2D>()

        for (y in map.indices) {
            for (x in map[y].indices) {
                val current = Point2D(x,y)
                val elevation = map.at(current)
                if (elevation == 0)
                    trailheads.add(current)
            }
        }

        return trailheads
    }

    data class State(val point: Point2D, var count: Int)

    private fun List<List<Int>>.at(point: Point2D): Int = this[point.y][point.x]

    private fun List<List<Int>>.isOnMap(point: Point2D): Boolean =
        point.x in this[0].indices && point.y in this.indices

    private fun List<List<Int>>.isUphill(a: Point2D, b: Point2D): Boolean =
        isOnMap(a) && isOnMap(b) && at(b) == at(a) + 1

    private fun List<List<Int>>.getUphillNeighbors(point: Point2D): List<Point2D> =
        point.neighbors4().filter { neighbor -> isUphill(point, neighbor) }

    private fun Point2D.walkFrom(map: List<List<Int>>): Sequence<State> = sequence {
        val trailhead = this@walkFrom
        val pathsCount = mutableMapOf(trailhead to 1)
        val queue = ArrayDeque<Point2D>()

        queue.add(trailhead)

        while (queue.size > 0) {
            val current = queue.removeFirst()

            yield(State(current, pathsCount[current]!!))

            for (neighbor in map.getUphillNeighbors(current)) {
                if (pathsCount.containsKey(neighbor))
                    pathsCount[neighbor] = pathsCount[neighbor]!! + pathsCount[current]!!
                else {
                    pathsCount[neighbor] = pathsCount[current]!!
                    queue.add(neighbor)
                }
            }
        }
    }

    private fun Point2D.getScore(map: List<List<Int>>): Int =
        this.walkFrom(map).count { state -> map.at(state.point) == 9 }

    private fun Point2D.getRating(map: List<List<Int>>): Int =
        this.walkFrom(map).filter { state -> map.at(state.point) == 9 }.sumOf { state -> state.count }
}

