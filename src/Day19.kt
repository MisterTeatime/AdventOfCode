fun main() {
    fun part1(input: List<String>): Int {
        val (words, sentences) = splitInput(input)

        val result = sentences.map { isValidSequence(it, words) }
        return result.count { it }
    }

    fun part2(input: List<String>): Long {
        val (words, sentences) = splitInput(input)

        val result = sentences.map { countValidCombinations(it, words) }
        return result.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 6)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 16L)

    val input = readInput("Day19")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun splitInput(input: List<String>): Pair<List<String>, List<String>> {
    val words = input[0].split(", ")
    val sentences = input.drop(2)

    return words to sentences
}

fun isValidSequence(sentence: String, words: List<String>): Boolean {
    val wordSet = words.toHashSet()
    val memo = mutableMapOf<Int, Boolean>()

    fun canForm(start: Int): Boolean {
        if (start == sentence.length) return true

        if (memo.containsKey(start)) return memo[start]!!

        for (end in start + 1..sentence.length) {
            val word = sentence.substring(start, end)
            if (word in wordSet && canForm(end)) {
                memo[start] = true
                return true
            }
        }
        memo[start] = false
        return false
    }
    return canForm(0)
}

fun countValidCombinations(sentence: String, words: List<String>): Long {
    val wordSet = words.toHashSet()
    val memo = mutableMapOf<String, Long>()

    fun countWays(startIndex: Int): Long {
        if (startIndex == sentence.length) return 1
        if (memo.containsKey(startIndex.toString())) return memo[startIndex.toString()]!!

        var totalWays = 0L

        for (word in wordSet) {
            if (sentence.startsWith(word, startIndex)) {
                totalWays += countWays(startIndex + word.length)
            }
        }
        memo[startIndex.toString()] = totalWays
        return totalWays
    }
    return countWays(0)
}