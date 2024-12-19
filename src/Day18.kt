import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>, bytes: Int = 1024, start: Point2D = Point2D(0,0), goal: Point2D = Point2D(70,70)): Int {

        val obstacles = getObstacleList(input, bytes)
        val shortestPath = findShortestPath(start, goal, obstacles)

        if (shortestPath != null) return shortestPath.size - 1
        return 0
    }

    fun part2(input: List<String>, bytes: Int = 1024, start: Point2D = Point2D(0,0), goal: Point2D = Point2D(70,70)): Point2D {
        val obstacles = getObstacleList(input, bytes)
        var shortestPath = findShortestPath(start, goal, obstacles)

        for (i in bytes + 1 until input.size) {
            val (first, last) = input[i].split(",")
            val newObstacle = Point2D(first.toInt(), last.toInt())
            println(newObstacle)

            if (shortestPath!!.contains(newObstacle)) {
                obstacles.add(newObstacle)
                shortestPath = findShortestPath(start, goal, obstacles)
            }

            if (shortestPath == null) return newObstacle
        }
        return Point2D(-1, -1)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    val resultPart1 = part1(testInput, 12, goal = Point2D(6,6))
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 22)

    val resultPart2 = part2(testInput, 12, goal = Point2D(6,6))
    println("Test Part 2: $resultPart2")
    check(resultPart2 == Point2D(6,1))

    val input = readInput("Day18")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

data class Node(val point: Point2D, val g: Int, val h: Int, val parent: Node?) {
    val f: Int get() = g + h

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (point != other.point) return false
        return true
    }

    override fun hashCode(): Int {
        return point.hashCode()
    }
}

fun getObstacleList(input: List<String>, bytes: Int): MutableList<Point2D> = input
    .take(bytes)
    .map {
        val (first, last) = it.split(",")
        Point2D(first.toInt(), last.toInt())
    }
    .toMutableList()

fun findShortestPath(start: Point2D, goal: Point2D, obstacles: List<Point2D>): List<Point2D>? {
    val xIndices = start.x .. goal.x
    val yIndices = start.y .. goal.y

    fun isOnMap(point: Point2D): Boolean =
        point.x in xIndices && point.y in yIndices

    val openSet = PriorityQueue<Node> { a, b -> a.f - b.f }
    openSet.add(Node(start, 0, start.distanceTo(goal), null))

    val closedSet = mutableSetOf<Node>()

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()
        if (current.point == goal) {
            val path = mutableListOf<Node>()
            var node = current
            while (node != null) {
                path.add(node)
                node = node.parent
            }
            path.reverse()
            return path.map { it.point }
        }

        var neighborsUsed = false

        for (neighbor in current.point.neighbors4()) {
            if (!isOnMap(neighbor)) continue
            if (obstacles.contains(neighbor)) continue

            val newNode = Node(neighbor, current.g + 1, neighbor.distanceTo(goal), current)
            val existingNode = openSet.find { it.point == newNode.point }
            if (existingNode != null) {
                if (newNode.g >= existingNode.g) continue
                openSet.remove(existingNode)
            }
            openSet.add(newNode)
            neighborsUsed = true
        }

        if (!neighborsUsed)
            closedSet.add(current)
    }

    return null
}