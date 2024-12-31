package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*

class Day04 {
    private val testInput = readInput("""2024\Day04_test""")
    private val input = readInput("""2024\Day04""")

    fun solvePart1(input: List<String> = this.input): Int = timing {
        val word = "XMAS"
        val targetWords = listOf(word, word.reversed())
        val wordLength = word.length

        val sequences =
            input.getStringRows() +
                    input.getStringColumns() +
                    input.getStringNWSODiagonals(wordLength) +
                    input.getStringNOSWDiagonals(wordLength)

        val results = mutableListOf<Pair<Int, Int>>()
        for ((sequence, startPos, direction) in sequences) {
            for (w in targetWords) {
                var index = sequence.indexOf(w)
                while (index != -1) {
                    val startRow = startPos.first + index * direction.first
                    val startCol = startPos.second + index * direction.second
                    results.add(startRow to startCol)

                    index = sequence.indexOf(w, index + 1)
                }
            }
        }

        return@timing results.size
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing {
        val results = mutableListOf<Pair<Int, Int>>()

        val numRows = input.size
        val numCols = input[0].length

        val directions = listOf(
            Pair(Pair(-1, -1), Pair(1, 1)),
            Pair(Pair(-1, 1), Pair(1, -1))
        )
        for (row in 1 ..< numRows - 1) {
            for (col in 1 ..< numCols - 1) {
                if (input[row][col] == 'A') {
                    var valid = true
                    for ((start, end) in directions) {
                        val (sRow, sCol) = Pair(row + start.first, col + start.second)
                        val (mRow, mCol) = Pair(row + end.first, col + end.second)

                        if (!((input[sRow][sCol] == 'S' && input[mRow][mCol] == 'M') ||
                                    (input[sRow][sCol] == 'M' && input[mRow][mCol] == 'S'))) {
                            valid = false
                            break
                        }
                    }

                    if (valid)
                        results.add(Pair(row, col))
                }
            }
        }

        return@timing results.size
    }

    fun testPart2() = solvePart2(testInput)
}

