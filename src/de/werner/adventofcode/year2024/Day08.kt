package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*

class Day08 {
    private val testInput = readInput("""2024\Day08_test""")
    private val input = readInput("""2024\Day08""")

    fun solvePart1(input: List<String> = this.input): Int = timing {

        val frequencies = findFrequencies(input)
        val antinodes = calculateUniqueFirstAntinodes(frequencies, input)

        return@timing antinodes.size
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing {
        val frequencies = findFrequencies(input)
        val antinodes = calculateUniqueAntinodes(frequencies, input)

        return@timing antinodes.size
    }

    fun testPart2() = solvePart2(testInput)

    private fun findFrequencies(map: List<String>): Map<Char, MutableList<Point2D>> {
        val result = mutableMapOf<Char, MutableList<Point2D>>()

        for (y in map.indices) {
            for (x in map[y].indices) {
                val symbol = map[y][x]

                if (symbol != '.') {
                    result.computeIfAbsent(symbol) { mutableListOf() }
                        .add(Point2D(x, y))
                }
            }
        }

        return result
    }

    private fun calculateUniqueFirstAntinodes(frequencies: Map<Char, List<Point2D>>, map: List<String>): List<Point2D> {
        val antinodes = mutableSetOf<Point2D>()

        for ((symbol, points) in frequencies) {
            if (points.size < 2) {
                println("Frequenz '$symbol' übersprungen. Nur ${points.size} Antennen")
                continue
            }

            for (i in points.indices) {
                for (j in i+1 until points.size) {
                    val antinodePoints = points[i].calculateFirstAntinodes(points[j])
                    antinodes.addAll(antinodePoints.filter { isWithinBounds(it, map)})
                }
            }
        }

        return antinodes.toList()
    }

    private fun calculateUniqueAntinodes(frequencies: Map<Char, List<Point2D>>, map: List<String>): List<Point2D> {
        val antinodes = mutableSetOf<Point2D>()

        for ((symbol, points) in frequencies) {
            if (points.size < 2) {
                println("Frequenz '$symbol' übersprungen. Nur ${points.size} Antennen")
                continue
            }

            for (i in points.indices) {
                for (j in i+1 until points.size) {
                    val antinodePoints = points[i].calculateAllAntinodes(points[j], map)
                    antinodes.addAll(antinodePoints)
                }
            }
        }

        return antinodes.toList()
    }

    private fun isWithinBounds(point: Point2D, map: List<String>) =
        point.x in map[0].indices && point.y in map.indices

    private fun Point2D.calculateFirstAntinodes(other: Point2D): List<Point2D> {
        val slope = slopeTo(other)
        return listOf(
            this - slope,
            other + slope
        )
    }

    private fun Point2D.calculateAllAntinodes(other: Point2D, map: List<String>): List<Point2D> {
        val slope = slopeTo(other)
        val antinodes = mutableListOf<Point2D>()

        var currentPoint = this
        while (isWithinBounds(currentPoint, map)) {
            antinodes.add(currentPoint)
            currentPoint -= slope
        }

        currentPoint = other
        while (isWithinBounds(currentPoint, map)) {
            antinodes.add(currentPoint)
            currentPoint += slope
        }

        return antinodes
    }
}

