fun main() {
    fun part1(input: List<String>): Long {
        val startNumbers = input.map { it.toLong() }
        val sum = sumOfNthSecretNumber(startNumbers, 2000)

        return sum
    }

    fun part2(input: List<String>): Long {
        val startNumbers = input.map { it.toLong() }
        val (optimalSequence, maxProfit) = findOptimalDiffSequence(startNumbers, 4)
        println(optimalSequence)
        return maxProfit
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
        val e1 = ((currentNumber shl 6) xor currentNumber) and 0xFFFFFFL
        val e2 = ((e1 shr 5) xor e1) and 0xFFFFFFL
        currentNumber = ((e2 shl 11) xor e2) and 0xFFFFFFL
        yield(currentNumber)
    }
}

fun sumOfNthSecretNumber(startNumbers: List<Long>, n: Int): Long {
    var sum = 0L
    for (startNumber in startNumbers) {
        sum += generateSecretNumberSequence(startNumber).elementAt(n - 1)
    }
    return sum
}

fun findMaxProfitForSequence(startNumbers: List<Long>, targetDiffs: List<Int>): Long {
    val diffCount = targetDiffs.size
    var maxProfit = 0L

    startNumbers.forEach { startNumber ->
        //val seenDiffs = mutableSetOf<List<Int>>()
        val secretNumbers = generateSecretNumberSequence(startNumber).iterator()

        val currentDiffs = mutableListOf<Int>()
        var lastPrice = secretNumbers.next() % 10
        var position = 0

        while (position < 2000 && currentDiffs.size < diffCount) {
            val currentPrice = secretNumbers.next() % 10
            val diff = (currentPrice - lastPrice).toInt()
            lastPrice = currentPrice

            if (currentDiffs.size == diffCount) {
                currentDiffs.removeAt(0)
            }

            currentDiffs.add(diff)

            if (currentDiffs == targetDiffs) {

                maxProfit += currentPrice
                break
            }

            position++
        }
    }
    return maxProfit
}

fun generateAllDiffSquences(length: Int, range: IntRange): List<List<Int>> {
    if (length == 1) return range.map { listOf(it)}

    val shorterSequences = generateAllDiffSquences(length - 1, range)
    return shorterSequences.flatMap { seq -> range.map { seq + it }}
}

fun findOptimalDiffSequence(
    startNumbers: List<Long>,
    sequenceLength: Int
): Pair<List<Int>, Long> {
    val allDiffSequences = generateAllDiffSquences(sequenceLength, -9..9)
    var maxProfit = 0L
    var optimalSequence: List<Int> = emptyList()

    for (sequence in allDiffSequences) {
        val profit = findMaxProfitForSequence(startNumbers, sequence)
        if (profit > maxProfit) {
            maxProfit = profit
            optimalSequence = sequence
        }
    }
    return optimalSequence to maxProfit
}