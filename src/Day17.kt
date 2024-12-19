fun main() {
    fun part1(input: List<String>): String {
        return ""
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == "4,6,3,5,6,3,5,2,1,0")

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day17")
    println("Part 1: ${part1(input)}")
//    println("Part 2: ${part2(input)}")
}

//fun Pair<Int, Int>.processInstruction(registers: MutableMap<Char, Int>): List<Int> {
//    val (opcode, operand) = this
//
//    when (opcode) {
//        0 -> registers['A'] = registers['A']?.shr(operand.getComboOperand(registers).toInt()) ?: throw IllegalArgumentException("Register A is null") //adv
//        1 -> registers['B'] = registers['B']?.xor(operand) ?: throw IllegalArgumentException("Register B is null") //bxl
//        2 -> registers['B'] = operand % 8
//    }
//}

fun Int.getComboOperand(registers: Map<Char, Int>): Int = when (this) {
    in 0..3 -> this
    4 -> registers['A']!!
    5 -> registers['B']!!
    6 -> registers['C']!!
    7 -> throw IllegalArgumentException("Operand 7 ist reserviert sollte nicht verwendet werden")
    else -> throw IllegalArgumentException("Operand $this ist ungültig")
}