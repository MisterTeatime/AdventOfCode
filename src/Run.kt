import de.werner.adventofcode2024.*

fun main() {
    val testInput = readInput("Day25_test")
    val day = Day25()

    val resultPart1 = day.solvePart1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 3)

//    val resultPart2 = day.solvePart2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 31)

    val input = readInput("Day25")

    println("Part 1: ${day.solvePart1(input)}")
//    println("Part 2: ${day.solvePart2(input)}")
}