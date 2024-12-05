fun main() {
    fun splitList(input: List<String>): Pair<List<String>, List<String>> {
        val splitIndex = input.indexOf("")

        return if (splitIndex == -1)
            Pair(emptyList<String>(), emptyList<String>())
        else {
            val before = input.take(splitIndex)
            val after = input.drop(splitIndex + 1)
            Pair(before, after)
        }
    }

    fun isValidUpdate(instructions: List<Pair<Int, Int>>, update: List<Int>): Boolean = instructions
        .filter { (first, second) -> update.contains(first) && update.contains(second) }
        .all { (before, after) ->
            val indexAfter = update.indexOf(after)
            val indexBefore = update.indexOf(before)
            indexBefore < indexAfter
        }

    fun correctUpdate(instructions: List<Pair<Int, Int>>, update: List<Int>): List<Int> {
        val relevantInstructions = instructions.filter { (first, second) ->
            update.contains(first) && update.contains(second)
        }
        return update.sortedWith { a, b ->
            when {
                relevantInstructions.any { it.first == a && it.second == b } -> -1
                relevantInstructions.any { it.first == b && it.second == b } -> 1
                else -> 0
            }
        }
    }

    fun part1(input: List<String>): Int {
        val (inst, upd) = splitList(input)

        val instructions = inst.map { instruction ->
            val (first, second) = instruction.split("|").map { it.toInt() }
            first to second
        }
        val updates = upd.map { it.split(",").map { el -> el.toInt() } }

        val validUpdates = updates.filter { isValidUpdate(instructions, it)}
        return validUpdates.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val (inst, upd) = splitList(input)

        val instructions = inst.map { instruction ->
            val (first, second) = instruction.split("|").map { it.toInt() }
            first to second
        }
        val updates = upd.map { it.split(",").map { el -> el.toInt() } }

        val correctedUpdates = updates.mapNotNull { update ->
            if (!isValidUpdate(instructions, update)) {
                correctUpdate(instructions, update)
            } else {
                null
            }
        }

        return correctedUpdates.sumOf { it[it.size / 2] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 143)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 123)

    val input = readInput("Day05")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
