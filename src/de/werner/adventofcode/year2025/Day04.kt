package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

private const val BOX: Char = '@'
private const val EMPTY: Char = '.'

class Day04 {
    private val testInput = readInput("""2025\Day04_test""")
    private val input = readInput("""2025\Day04""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        countAccessibleRolls(input.map { it.toMutableList() })
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        countAndRemoveAccessibleRolls(input)
    }

    fun testPart2() = solvePart2(testInput)

    fun countAccessibleRolls(board: List<MutableList<Char>>): Long {
        val rows = board.size
        val cols = board[0].size
        var count = 0L

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val point = Point2D(j, i)
                if (board[i][j] == BOX && isAccessible(board, point, rows, cols)) {
                    //println(point)
                    count++
                }
            }
        }
        return count
    }

    fun countAndRemoveAccessibleRolls(board: List<String>): Long {
        val mutableBoard = board.map { it.toMutableList() }
        var totalRemoved = 0L

        while (true) {
            val toRemove = collectAccessiblePoints(mutableBoard)
            if (toRemove.isEmpty()) break

            toRemove.forEach { mutableBoard[it.y][it.x] = EMPTY}
            totalRemoved += toRemove.size
        }

        return totalRemoved
    }

    fun collectAccessiblePoints(board: List<MutableList<Char>>): MutableList<Point2D> {
        val accessiblePoints = mutableListOf<Point2D>()

        for (row in board.indices) {
            for (col in 0 until board[0].size) {
                val point = Point2D(col, row)
                if (board[row][col] == BOX && isAccessible(board, point, board.size, board[0].size)) {
                    accessiblePoints.add(point)
                }
            }
        }
        return accessiblePoints
    }

    private fun isAccessible(board: List<MutableList<Char>>, point: Point2D, rows: Int, cols: Int): Boolean {
        var neighborCount = 0

        for (neighbor in point.neighbors8()) {
            if (
                neighbor.x in 0 until cols
                && neighbor.y in 0 until rows
                && board[neighbor.y][neighbor.x] == BOX
                ) {
                neighborCount++
            }
        }

        return neighborCount < 4
    }
}

