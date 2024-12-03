fun main() {
    fun extractMultiplications(input: String): List<Pair<Int, Int>> {
        // Regex für die Multiplikationsanweisung: mul(X,Y) mit 1-3-stelligen Zahlen
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

        // Finden aller Matches
        return regex.findAll(input).map { matchResult ->
            // Extrahieren der Gruppen 1 und 2 (X und Y) und Konvertierung zu Int
            val x = matchResult.groupValues[1].toInt()
            val y = matchResult.groupValues[2].toInt()
            Pair(x, y) // Rückgabe als Tupel
        }.toList() // Umwandeln in eine Liste
    }
    fun part1(input: List<String>): Int {

        val mults = input.map { extractMultiplications(it) }.flatten()

        return mults.sumOf { (x, y) -> x * y }
    }

    fun processInstructions(input: String): List<Pair<Int, Int>> {
        val regex = Regex("""(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""")
        val matches = regex.findAll(input)

        var isEnabled = true
        val result = mutableListOf<Pair<Int,Int>>()

        for (match in matches) {
            val token = match.value

            when {
                token.startsWith("do()") -> isEnabled = true
                token.startsWith("don't()") -> isEnabled = false
                token.startsWith("mul(") && isEnabled -> {
                    val numbers = Regex("""\d{1,3}""")
                        .findAll(token)
                        .map {it.value.toInt() }
                        .toList()
                    result.add(Pair(numbers[0], numbers[1]))
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val mults = processInstructions(input.joinToString(""))

        return mults.sumOf { (a,b) -> a*b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 161)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 48)

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
