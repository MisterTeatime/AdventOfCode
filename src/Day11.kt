import kotlin.math.pow
import kotlinx.coroutines.*

fun main() {
    fun part1(input: List<String>): Int {
        var result = input[0].split(" ").map { it.toLong() }

        repeat(25) {
            result = processListParallel(result)
        }

        return result.size
    }

    fun part2(input: List<String>): Long {
        var result = input[0]
            .split(" ")
            .groupingBy { it.toLong() }
            .eachCount()
            .mapValues { it.value.toLong() }

        repeat(75) {
            result = processIterationWithCount(result)
        }

        return result.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 55312)

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day11")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun applyRules(input: List<Long>): List<Long> {
    require(input.size == 1) {"Input Liste muss genau ein Element enthalten."}
    val number = input.first()
    val cache = mutableMapOf<Long, List<Long>>()

    return when {
        //Regel 1: 0 wird 1
        number == 0L -> cache.computeIfAbsent(number) { listOf(1L) }

        //Regel 2: Gerade Stellenanzahl wird aufgeteilt
        number.toString().length % 2 == 0 -> cache.computeIfAbsent(number) { applyRule2(number) }

        //Regel 3: Zahl mit 2024 multiplizieren
        else -> cache.computeIfAbsent(number) { listOf(number * 2024) }
    }
}

fun processListParallel(input: List<Long>): List<Long> = input.parallelStream()
    .flatMap { applyRules(listOf(it)).stream() }
    .toList()

fun processIterationWithCount(input: Map<Long, Long>): Map<Long, Long> {
    val result = mutableMapOf<Long, Long>()

    for ((value, count) in input) {
        val transformedValue = applyRules(listOf(value))

        for (newValue in transformedValue) {
            result[newValue] = count + result.getOrDefault(newValue, 0L)
        }
    }

    return result
}

fun applyRule2(number: Long): List<Long> {
    val powerOfTen = 10.0.pow((number.toString().length / 2).toDouble()).toLong()
    val firstPart = number / powerOfTen
    val secondPart = number % powerOfTen
    return listOf(firstPart, secondPart)
}