package de.werner.adventofcode

import de.werner.adventofcode.year2025.*

fun main() {
    val day = Day08()

    val resultPart1 = day.testPart1()
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 40)
    println("Part 1: ${day.solvePart1()}")

    val resultPart2 = day.testPart2()
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 25272L)
    println("Part 2: ${day.solvePart2()}")
}