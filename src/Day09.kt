fun main() {
    fun part1(input: List<String>): Long {
        val (blocks, spaces) = splitInput(input[0])
        return defragAndCalculateChecksum(blocks, spaces)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 1928L)

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day09")
    println("Part 1: ${part1(input)}")
//    println("Part 2: ${part2(input)}")
}

fun splitInput(input: String): Pair<MutableList<Int> ,MutableList<Int>> {
    val numbers = input.map { it.toString().toInt() }
    val blocks = numbers.filterIndexed { index, _ -> index % 2 == 0 }
    val spaces = numbers.filterIndexed { index, _ -> index % 2 != 0 }
    return Pair(blocks.toMutableList(), spaces.toMutableList())
}

fun defragAndCalculateChecksum(blocks: MutableList<Int>, spaces: MutableList<Int>): Long {
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