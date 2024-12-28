import de.werner.adventofcode2024.*

fun main() {
    val day = Day24()

    val resultPart1 = day.testPart1()
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 2024L)

//    val resultPart2 = day.testPart2()
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 31)

    println("Part 1: ${day.solvePart1()}")
//    println("Part 2: ${day.solvePart2()}")
}