import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val diffs = input.map {
            line -> line
                .split(" ")
                .map { it.toInt() }
                .zipWithNext { a, b -> b - a }
        }.map { when {
            !it.all { el -> abs(el) in 1..3 } -> false
            it.all { el -> el > 0 } || it.all { el -> el < 0 } -> true
            else -> false
        }
        }

        return diffs.count { it }
    }

    fun part2(input: List<String>): Int {
        val diffs = input.map { line ->
            val numbers = line.split(" ").map { it.toInt() }

            // Prüft, ob die Sequenz ohne Änderungen gültig ist
            fun isValid(sequence: List<Int>): Boolean {
                val differences = sequence.zipWithNext { a, b -> b - a }
                return differences.all { abs(it) in 1..3 } &&
                        (differences.all { it > 0 } || differences.all { it < 0 })
            }

            // Prüft die ursprüngliche Sequenz und alle möglichen Sequenzen mit einem entfernten Element
            if (isValid(numbers)) {
                true
            } else {
                numbers.indices.any { index ->
                    val reducedSequence = numbers.toMutableList().apply { removeAt(index) }
                    isValid(reducedSequence)
                }
            }
        }


        return diffs.count {it}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 2)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 4)

    val input = readInput("Day02")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
