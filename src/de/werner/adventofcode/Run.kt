package de.werner.adventofcode

import de.werner.adventofcode.year2025.*
import kotlin.system.exitProcess

fun <T> verifyThenRun(
    name: String,
    test: () -> T,
    expected: T? = null,
    solve: () -> T,
    skipTest: Boolean = false
) {
    if (!skipTest) {
        val got = try {
            test()
        } catch (e: Throwable) {
            System.err.println("Test $name: threw exception: ${e.message}")
            e.printStackTrace()
            exitProcess(1)
        }
        println("Test $name: $got")
        if (expected != null && got != expected) {
            System.err.println("Test FAILED for $name: expected=$expected got=$got")
            exitProcess(1)
        }
    } else {
        println("Skipping tests for $name")
    }

    val solution = try {
        solve()
    } catch (e: Throwable) {
        System.err.println("Solution $name: threw exception: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
    println("Solution $name: $solution")
}

fun main() {
    val day = Day12()

    verifyThenRun(
        name = "Day12 Part1",
        test = { day.testPart1() },
        expected = day.expectedPart1,
        solve = { day.solvePart1() }
    )

    verifyThenRun(
        name = "Day12 Part2",
        test = { day.testPart2() },
        expected = day.expectedPart2,
        solve = { day.solvePart2() }
    )
}
