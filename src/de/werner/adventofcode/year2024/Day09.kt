package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*

class Day09 {
    private val testInput = readInput("""2024\Day09_test""")
    private val input = readInput("""2024\Day09""")

   fun solvePart1(input: List<String> = this.input): Long = timing {
       val (blocks, spaces) = splitInput(input[0])
       return@timing defragAndCalculateChecksum(blocks, spaces)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing { input.size }

    fun testPart2() = solvePart2(testInput)

    private fun splitInput(input: String): Pair<MutableList<Int> ,MutableList<Int>> {
        val numbers = input.map { it.toString().toInt() }
        val blocks = numbers.filterIndexed { index, _ -> index % 2 == 0 }
        val spaces = numbers.filterIndexed { index, _ -> index % 2 != 0 }
        return Pair(blocks.toMutableList(), spaces.toMutableList())
    }

    private fun defragAndCalculateChecksum(blocks: MutableList<Int>, spaces: MutableList<Int>): Long {
        if (blocks.isEmpty()) return 0L

        var blockIndex = 0
        var blockPivot = 0
        var spacePivot = 0
        var fillingPivot = blocks.size - 1
        var checksum = 0L

        while (blockPivot < blocks.size) {
            //Block übernehmen und Checksumme anpassen
            while (blocks[blockPivot] > 0) {
                checksum += blockPivot * blockIndex++
                blocks[blockPivot]--
            }
            blockPivot++

            //Lücke füllen und Checksumme anpassen
            if (blocks[fillingPivot] > 0) {
                while (spacePivot < spaces.size && spaces[spacePivot] > 0) {
                    checksum += fillingPivot * blockIndex++
                    blocks[fillingPivot]--
                    spaces[spacePivot]--

                    if (blocks[fillingPivot] == 0)
                        fillingPivot--
                }
                spacePivot++
            }
        }

        return checksum
    }
}

