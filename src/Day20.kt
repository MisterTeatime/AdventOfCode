fun main() {
    fun part1(input: List<String>): Int {
        val cheatTimes = findCheatsWithDistance(input, 2)
        return cheatTimes
            .filter { it >= 100 }
            .size
    }

    fun part2(input: List<String>): Int {
        val cheatTimes = findCheatsWithDistance(input, 20)
        return cheatTimes
            .filter { it >= 100 }
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    //check(resultPart1 == 1)

    val resultPart2 = part2(testInput)
    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day20")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun findCheatsWithDistance(
    track: List<String>,
    maxDistance: Int
): List<Int> {
    var start: Point2D? = null
    var end: Point2D? = null
    val points = mutableSetOf<Point2D>()

    for (y in track.indices) {
        for (x in track[y].indices) {
            val point = Point2D(x,y)
            when (track[y][x]) {
                'S' -> {
                    start = point
                    points.add(point)
                }
                'E' -> {
                    end = point
                    points.add(point)
                }
                '.' -> points.add(point)
            }
        }
    }

    requireNotNull(start)
    requireNotNull(end)

    val cumulativeTime = mutableMapOf(start to 0)
    val queue = ArrayDeque<Point2D>()
    queue.add(start)

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val currentTime = cumulativeTime[current]!!

        for (neighbor in current.neighbors4()) {
            if (neighbor in points && neighbor !in cumulativeTime) {
                cumulativeTime[neighbor] = currentTime + 1
                queue.add(neighbor)
            }
        }
    }

    val cheatTimes = mutableListOf<Int>()

    for (point in points) {
        val startTime = cumulativeTime[point] ?: continue

        for (target in points) {
            val cheatDistance = point.distanceTo(target)
            if (cheatDistance in 1..maxDistance) {
                val regularTime = (cumulativeTime[target] ?: continue) - startTime
                val timeSaved = regularTime - cheatDistance

                if (timeSaved > 0) {
                    cheatTimes.add(timeSaved)
                }
            }
        }
    }
    return cheatTimes
}