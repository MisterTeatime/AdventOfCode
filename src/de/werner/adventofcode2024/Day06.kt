package de.werner.adventofcode2024

import Point2D

class Day06 {
    fun solvePart1(input: List<String>): Int {

        val start = findStartPosition(input)

        if (start != null) {
            val path = simulatePath(input, start)
            return path.first.size
        } else {
            throw Exception("Keine Startposition gefunden!")
        }
    }

    fun solvePart2(input: List<String>): Int {
        val start = findStartPosition(input)

        if (start != null) {
            //Hindernisse finden
            val loopPositions = findLoopPositions(input, start)
            return loopPositions.size
        }
        else {
            throw Exception("Keine Startposition gefunden!")
        }
    }

    private fun findStartPosition(map: List<String>): Point2D? {
        for (row in map.indices) {
            val col = map[row].indexOf("^")
            if (col != -1) return Point2D(col to row)
        }
        return null
    }

    private fun simulatePath(map: List<String>, start: Point2D): Pair<Set<Point2D>, Boolean> {
        val directions = listOf( //Richtungen im Uhrzeigersinn
            Point2D(0,-1),      //Oben
            Point2D(1,0),       //Rechts
            Point2D(0, 1),      //Unten
            Point2D(-1, 0),     //Links
        )

        var directionIndex = 0
        var position = start

        val visited = mutableSetOf<Pair<Point2D, Int>>()

        while (true) {
            val state = position to directionIndex

            if (visited.contains(state))
                return visited.map { it.first }.toSet() to true

            visited.add(state)
            val next = position + directions[directionIndex]

            when {
                (next.x !in map[0].indices || next.y !in map.indices) -> break
                map[next.y][next.x] == '#' -> directionIndex = (directionIndex + 1) % directions.size
                else -> position = next
            }
        }

        return visited.map {it.first}.toSet() to false
    }

    private fun findLoopPositions(map: List<String>, start: Point2D): List<Point2D> {
        val loopPositions = mutableListOf<Point2D>()

        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[x][y] == '.') {
                    val modifiedMap = map.mapIndexed { row, line ->
                        if (row == y) line.substring(0, x) + '#' + line.substring(x + 1) else line
                    }
                    val (_, hasLoop) = simulatePath(modifiedMap, start)

                    if (hasLoop)
                        loopPositions.add(Point2D(x,y))
                }
            }
        }

        return loopPositions
    }
}

