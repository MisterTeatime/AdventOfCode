fun main() {
    fun part1(input: List<String>): Long {
        val startNumbers = input.map { it.toLong() }
        val sum = sumOfNthSecretNumber(startNumbers, 2000)

        return sum
    }

    fun part2(input: List<String>): Long {
        val startNumbers = input.map { it.toLong() }
        val diffPrices = mutableMapOf<List<Long>, Long>()
//        findBuyerPrices(123L, diffPrices)
//        findBuyerPrices(1L, diffPrices)
        startNumbers.forEach { findBuyerPrices(it, diffPrices) }

        return diffPrices.values.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
//    val resultPart1 = part1(testInput)
//    println("Test Part 1: $resultPart1")
//    check(resultPart1 == 37327623L)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 23L)

    val input = readInput("Day22")
//    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun generateSecretNumberSequence(startNumber: Long): Sequence<Long> = sequence {
    var currentNumber = startNumber

    while (true) {
        currentNumber = calculateNextSecretNumber(currentNumber)
        yield(currentNumber)
    }
}

fun calculateNextSecretNumber(startNumber: Long): Long {
    var currentNumber = startNumber

    currentNumber = ((currentNumber shl 6) xor currentNumber) and 0xFFFFFFL
    currentNumber = ((currentNumber shr 5) xor currentNumber) and 0xFFFFFFL
    currentNumber = ((currentNumber shl 11) xor currentNumber) and 0xFFFFFFL
    return currentNumber
}

fun sumOfNthSecretNumber(startNumbers: List<Long>, n: Int): Long {
    var sum = 0L
    for (startNumber in startNumbers) {
        sum += generateSecretNumberSequence(startNumber).elementAt(n - 1)
    }
    return sum
}

fun findBuyerPrices(startNumber: Long, diffPrices: MutableMap<List<Long>, Long>) {
    val visited = mutableSetOf<List<Long>>()
    val secretNumbers = generateSecretNumberSequence(startNumber).iterator()
    var currentPrice = startNumber % 10
    val currentPriceSequence = mutableListOf(currentPrice)

    repeat(2000) {
        currentPrice = secretNumbers.next() % 10
        currentPriceSequence.add(currentPrice)
        if (currentPriceSequence.size == 5) {
            val currentDiffs = currentPriceSequence.zipWithNext { a, b -> b - a }
            if (visited.add(currentDiffs)) {
                diffPrices[currentDiffs] = (diffPrices[currentDiffs] ?: 0L) + currentPrice
            }
            currentPriceSequence.removeFirst()
        }
    }
}