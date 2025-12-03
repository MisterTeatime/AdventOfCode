package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*
import kotlin.math.abs

class Day01 {
    private val testInput = readInput("""2025\Day01_test""")
    private val input = readInput("""2025\Day01""")

    fun solvePart1(input: List<String> = this.input): Int = timing {
        val dial = Dial(50)

        for (line in input) {
            when (line.take(1)) {
                "L" -> dial.turnLeft(line.drop(1).toInt())
                "R" -> dial.turnRight(line.drop(1).toInt())
            }
        }

        dial.zeroStops
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing {
        val dial = Dial(50)

        for (line in input) {
            when (line.take(1)) {
                "L" -> dial.turnLeft(line.drop(1).toInt())
                "R" -> dial.turnRight(line.drop(1).toInt())
            }
        }

        dial.zeroPasses + dial.zeroStops
    }

    fun testPart2() = solvePart2(testInput)

    class Dial(initial: Int = 0) {
        var position = initial % 100
            set(value) { field = ((value % 100) + 100) % 100}

        var zeroStops = 0
        var zeroPasses = 0

        fun turn(steps: Int) {
            val endPosition = (position + steps).mod(100)
            val fullTurn = abs(steps) / 100
            val crossZero = when {
                position == 0 || endPosition == 0 -> false
                steps > 0 -> position >= endPosition
                steps < 0 -> position <= endPosition
                else -> false
            }
            if (endPosition == 0) zeroStops++

            zeroPasses += fullTurn + if (crossZero) 1 else 0
            position = endPosition
        }

        fun turnLeft(steps: Int) = turn(-steps)

        fun turnRight(steps: Int) = turn(steps)

        override fun toString(): String = position.toString()
    }
}

