package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*
import kotlin.math.abs

class Day09 {
    private val testInput = readInput("""2025\Day09_test""")
    private val input = readInput("""2025\Day09""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        val tiles = parsePoints(input)
        var largestRect = 0L

        for (i in tiles.indices) {
            val p1 = tiles[i]
            for (j in i + 1 until tiles.size) {
                val p2 = tiles[j]

                val area = (abs(p2.x - p1.x) + 1).toLong() * (abs(p2.y - p1.y) + 1).toLong()

                if (area > largestRect) {
                    largestRect = area
                }
            }
        }
        largestRect
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val points = parsePoints(input)
        var largestRect = 0L

        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val rect = rectFromPoints(points[i], points[j])

                if (isRectValid(rect, points)) {  // Nur 4 Ecken!
                    largestRect = maxOf(largestRect, rect.area())
                }
            }
        }
        largestRect
    }

    fun testPart2() = solvePart2(testInput)

    fun parsePoints(input: List<String>): List<Point2D> = input.map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Point2D(x, y)
    }

    fun isRectValid(rect: Rect, polygon: List<Point2D>): Boolean {
        val points = polygon + polygon.first()
        val corners = listOf(
            Point2D(rect.x1, rect.y1),
            Point2D(rect.x1, rect.y2),
            Point2D(rect.x2, rect.y1),
            Point2D(rect.x2, rect.y2)
        )

        return corners.all { corner -> isPointInPolygon(corner, points) }
    }

    fun rectFromPoints(p1: Point2D, p2: Point2D): Rect =
        Rect(
            x1 = minOf(p1.x, p2.x),
            y1 = minOf(p1.y, p2.y),
            x2 = maxOf(p1.x, p2.x),
            y2 = maxOf(p1.y, p2.y)
        )

    fun isPointInPolygon(point: Point2D, polygon: List<Point2D>): Boolean {
        var inside = false
        val n = polygon.size

        for (i in 0 until n) {
            val curr = polygon[i]
            val next = polygon[(i + 1) % n]

            if (curr.y == point.y || next.y == point.y) continue

            if ((curr.y <= point.y) == (next.y > point.y)) {  // ← IntelliJ-Version!

                // FIX: Zwischenschritte als Double
                val dy = (next.y - curr.y).toDouble()
                val vt = (point.y - curr.y).toDouble() / dy
                val xinters = curr.x + vt * (next.x - curr.x).toDouble()

                if (point.x < xinters) {
                    inside = !inside
                }
            }
        }
        return inside
    }

}

data class Rect(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun area(): Long = (x2 - x1 + 1L) * (y2 - y1 + 1L)

    fun containsX(x: Int): Boolean = x in x1..x2
    fun containsY(y: Int): Boolean = y in y1..y2
}