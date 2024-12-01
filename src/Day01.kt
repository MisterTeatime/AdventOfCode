import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        for (line in input)
        {
            val (first, second) = line.split("   ").map { it.toInt() }
            firstList.add(first)
            secondList.add(second)
        }

        firstList.sort()
        secondList.sort()

        val combined = firstList.zip(secondList) { a, b -> abs(a - b) }

        return combined.sum()
    }

    fun part2(input: List<String>): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        for (line in input)
        {
            val (first, second) = line.split("   ").map { it.toInt() }
            firstList.add(first)
            secondList.add(second)
        }

        val result = firstList.map { it * secondList.count { elem -> elem == it } }

        return result.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 11)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 31)

    val input = readInput("Day01")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
