package de.werner.adventofcode2024

import Point2D

class Day15 {
    fun solvePart1(input: List<String>): Long {
        val (map, movementStrings) = splitList(input)
        val (objects, robotStart) = input.getMapObjects()

        if (robotStart == null) throw IllegalArgumentException("Keine Startposition gefunden")

        return input.size.toLong()
    }

    fun solvePart2(input: List<String>): Int = input.size

    data class Obstacle(val location: Point2D, val type: Char)

    private fun List<String>.getMapObjects(): Pair<List<Obstacle>, Point2D?> {
        val objects = mutableListOf<Obstacle>()
        var startPos: Point2D? = null

        for (y in this.indices) {
            for (x in this[y].indices) {
                when (this[y][x]) {
                    '#' -> objects.add(Obstacle(Point2D(x,y),'#'))
                    'O' -> objects.add(Obstacle(Point2D(x,y), 'O'))
                    '@' -> startPos = Point2D(x,y)
                }
            }
        }

        return (objects to startPos)
    }

    private fun Char.translateMovement(): Point2D =
        when (this) {
            '>' -> Point2D(1,0)
            '<' -> Point2D(-1,0)
            'v' -> Point2D(0,1)
            '^' -> Point2D(0,-1)
            else -> Point2D(0,0)
        }

    private fun splitList(input: List<String>): Pair<List<String>, List<Point2D>> {
        val splitIndex = input.indexOf("")

        return if (splitIndex == -1)
            Pair(emptyList(), emptyList())
        else {
            val before = input.take(splitIndex)
            val after = input.drop(splitIndex + 1)
            Pair(before, after.joinToString("").map { it.translateMovement() })
        }
    }
}

