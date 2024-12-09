fun main() {
    fun part1(input: List<String>): Int {

        val frequencies = findFrequencies(input)
        val antinodes = calculateUniqueFirstAntinodes(frequencies, input)

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val frequencies = findFrequencies(input)
        val antinodes = calculateUniqueAntinodes(frequencies, input)

        return antinodes.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 14)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
    check(resultPart2 == 34)

    val input = readInput("Day08")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun findFrequencies(map: List<String>): Map<Char, MutableList<Point2D>> {
    val result = mutableMapOf<Char, MutableList<Point2D>>()

    for (y in map.indices) {
        for (x in map[y].indices) {
            val symbol = map[y][x]

            if (symbol != '.') {
                result.computeIfAbsent(symbol) { mutableListOf() }
                    .add(Point2D(x, y))
            }
        }
    }

    return result
}

fun calculateUniqueFirstAntinodes(frequencies: Map<Char, List<Point2D>>, map: List<String>): List<Point2D> {
    val antinodes = mutableSetOf<Point2D>()

    for ((symbol, points) in frequencies) {
        if (points.size < 2) {
            println("Frequenz '$symbol' übersprungen. Nur ${points.size} Antennen")
            continue
        }

        for (i in points.indices) {
            for (j in i+1 until points.size) {
                val antinodePoints = points[i].calculateFirstAntinodes(points[j])
                antinodes.addAll(antinodePoints.filter { isWithinBounds(it, map)})
            }
        }
    }

    return antinodes.toList()
}

fun calculateUniqueAntinodes(frequencies: Map<Char, List<Point2D>>, map: List<String>): List<Point2D> {
    val antinodes = mutableSetOf<Point2D>()

    for ((symbol, points) in frequencies) {
        if (points.size < 2) {
            println("Frequenz '$symbol' übersprungen. Nur ${points.size} Antennen")
            continue
        }

        for (i in points.indices) {
            for (j in i+1 until points.size) {
                val antinodePoints = points[i].calculateAllAntinodes(points[j], map)
                antinodes.addAll(antinodePoints)
            }
        }
    }

    return antinodes.toList()
}

fun isWithinBounds(point: Point2D, map: List<String>) =
    point.x in map[0].indices && point.y in map.indices

fun Point2D.calculateFirstAntinodes(other: Point2D): List<Point2D> {
    val slope = slopeTo(other)
    return listOf(
        this - slope,
        other + slope
    )
}

fun Point2D.calculateAllAntinodes(other: Point2D, map: List<String>): List<Point2D> {
    val slope = slopeTo(other)
    val antinodes = mutableListOf<Point2D>()

    var currentPoint = this
    while (isWithinBounds(currentPoint, map)) {
        antinodes.add(currentPoint)
        currentPoint -= slope
    }

    currentPoint = other
    while (isWithinBounds(currentPoint, map)) {
        antinodes.add(currentPoint)
        currentPoint += slope
    }

    return antinodes
}