package de.werner.adventofcode2024

import readInput

class Day07 {
    private val testInput = readInput("""2024\Day07_test""")
    private val input = readInput("""2024\Day07""")

    fun solvePart1(input: List<String> = this.input): Long {
        return input
            .map { checkExpressionValidity(it)}
            .filter { it.first }
            .sumOf { it.second }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long {
        return input
            .map { checkExpressionValidity(it, 2)}
            .filter { it.first }
            .sumOf { it.second }
    }

    fun testPart2() = solvePart2(testInput)

    private fun checkExpressionValidity(line: String, part: Int = 1): Pair<Boolean, Long> {
        val (targetString, elementsString) = line.split(": ")
        val target = targetString.toLong()
        val elements = elementsString.split(" ").map { it.toLong()}

        return findValidExpression(elements, target, part) to target
    }

    private fun findValidExpression(
        elements: List<Long>,
        target: Long,
        part: Int = 1,
        currentResult: Long = 0,
        currentIndex: Int = 0)
            : Boolean {
        if (currentIndex == elements.size) {
            return currentResult == target
        }

        val currentElement = elements[currentIndex]

        if (currentIndex == 0)
            return findValidExpression(
                elements, target, part,
                currentResult = currentElement,
                currentIndex = currentIndex + 1
            )

        //Versuche +
        if (findValidExpression(
                elements, target, part,
                currentResult = currentResult + currentElement,
                currentIndex = currentIndex + 1
            ))
            return true

        //Versuche *
        if (findValidExpression(
                elements, target, part,
                currentResult = currentResult * currentElement,
                currentIndex = currentIndex + 1
            ))
            return true

        //Versuche ||
        if (part == 2)
            if (findValidExpression(
                    elements, target, part,
                    currentResult = (currentResult.toString() + currentElement.toString()).toLong(),
                    currentIndex = currentIndex + 1
                ))
                return true

        return false
    }
}

