import Extensions.pop
import Extensions.push

fun main() {
    fun part1(input: List<String>): Int {

        val (start, end) = input.findStartEnd()
        if (start == null || end == null) throw IllegalArgumentException("Start oder Ende nicht gefunden")

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 1)

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day16")
    println("Part 1: ${part1(input)}")
//    println("Part 2: ${part2(input)}")
}

fun List<String>.findStartEnd(): Pair<Point2D?, Point2D?> {
    var start: Point2D? = null
    var end: Point2D? = null

    for (y in this.indices) {
        for (x in this[y].indices) {
            when (this[y][x]) {
                'S' -> start = Point2D(x,y)
                'E' -> end = Point2D(x,y)
            }
        }
    }
    return (start to end)
}

fun Point2D.walkFrom(map: List<String>): Sequence<Point2D> = sequence{
    val stack = ArrayDeque<Point2D>()
    val visited = mutableSetOf<Point2D>()

    stack.push(this@walkFrom)

    while (stack.size > 0) {
        val current = stack.pop()

        if(!visited.add(current))
            yield(current)


    }
}