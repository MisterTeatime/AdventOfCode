package de.werner.adventofcode2024

import Point2D

class Day20 {
    fun solvePart1(input: List<String>): Int {
        val cheatTimes = findCheatsWithDistance(input, 2)
        return cheatTimes
            .filter { it >= 100 }
            .size
    }

    fun solvePart2(input: List<String>): Int {
        val cheatTimes = findCheatsWithDistance(input, 20)
        return cheatTimes
            .filter { it >= 100 }
            .size
    }

    private fun findCheatsWithDistance(
        track: List<String>,
        maxDistance: Int
    ): List<Int> {
        var start: Point2D? = null
        var end: Point2D? = null
        val points = mutableSetOf<Point2D>()

        for (y in track.indices) {
            for (x in track[y].indices) {
                val point = Point2D(x,y)
                when (track[y][x]) {
                    'S' -> {
                        start = point
                        points.add(point)
                    }
                    'E' -> {
                        end = point
                        points.add(point)
                    }
                    '.' -> points.add(point)
                }
            }
        }

        requireNotNull(start)
        requireNotNull(end)

        val cumulativeTime = mutableMapOf(start to 0)
        val queue = ArrayDeque<Point2D>()
        queue.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val currentTime = cumulativeTime[current]!!

            for (neighbor in current.neighbors4()) {
                if (neighbor in points && neighbor !in cumulativeTime) {
                    cumulativeTime[neighbor] = currentTime + 1
                    queue.add(neighbor)
                }
            }
        }

        val cheatTimes = mutableListOf<Int>()

        for (point in points) {
            val startTime = cumulativeTime[point] ?: continue

            for (target in points) {
                val cheatDistance = point.distanceTo(target)
                if (cheatDistance in 1..maxDistance) {
                    val regularTime = (cumulativeTime[target] ?: continue) - startTime
                    val timeSaved = regularTime - cheatDistance

                    if (timeSaved > 0) {
                        cheatTimes.add(timeSaved)
                    }
                }
            }
        }
        return cheatTimes
    }
}

