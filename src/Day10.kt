fun main() {
    fun part1(input: List<String>): Int {
        val trails = findTrailStarts(input)
        return 1
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 36)

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day10")
    println("Part 1: ${part1(input)}")
//    println("Part 2: ${part2(input)}")
}

fun findTrailStarts(map: List<String>): MutableList<MutableList<Point2D>> {
    val trails = mutableListOf<MutableList<Point2D>>()

    for (y in map.indices) {
        for (x in map[y].indices) {
            val elevation = map[y][x]
            if (elevation == '0')
                trails.add(mutableListOf(Point2D(x,y)))
        }
    }

    return trails
}

//