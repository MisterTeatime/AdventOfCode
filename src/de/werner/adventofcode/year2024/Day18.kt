package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*
import java.util.*

class Day18 {
    private val testInput = readInput("""2024\Day18_test""")
    private val input = readInput("""2024\Day18""")

    fun solvePart1(
        input: List<String> = this.input,
        bytes: Int = 1024,
        start: Point2D = Point2D(0,0),
        goal: Point2D = Point2D(70,70)
    ): Int = timing {

        val obstacles = getObstacleList(input.take(bytes))
        val shortestPath = findShortestPath(start, goal, obstacles)

        if (shortestPath != null) return@timing shortestPath.size - 1
        return@timing 0
    }

    fun testPart1() = solvePart1(input = testInput, bytes = 12, goal = Point2D(6,6))

    fun solvePart2(
        input: List<String>,
        width: Int = 71,
        height: Int = 71,
        start: Point2D = Point2D(0,0),
        goal: Point2D = Point2D(70,70)
    ): Point2D = timing {
        val obstacles = getObstacleList(input)

        var isConnected = true
        var blockingObstacle: Point2D? = null
        val grid = Array(height) { BooleanArray(width) { true } }

        for (obstacle in obstacles) {
            grid[obstacle.y][obstacle.x] = false

            val uf = UnionFind(width * height)

            for (y in 0 until height)
                for (x in 0 until width) {
                    if (grid[y][x]) {
                        if (x < width - 1 && grid[y][x + 1])
                            uf.union(y * width + x, y * width + (x + 1))
                        if (y < width - 1 && grid[y + 1][x])
                            uf.union(y * width + x, (y + 1) * width + x)
                    }
                }

            if (uf.find(start.y * width + start.x) != uf.find(goal.y * width + goal.x)) {
                isConnected = false
                blockingObstacle = obstacle
                break
            }
        }

        return@timing blockingObstacle ?: Point2D(-1, -1)
    }

    fun testPart2() = solvePart2(input = testInput, width = 7, height = 7, goal = Point2D(6,6))

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

    private fun getObstacleList(input: List<String>): MutableList<Point2D> = input
        .map {
            val (first, last) = it.split(",")
            Point2D(first.toInt(), last.toInt())
        }
        .toMutableList()

    private fun findShortestPath(start: Point2D, goal: Point2D, obstacles: List<Point2D>): List<Point2D>? {
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

    class UnionFind(size: Int) {
        private val parent = IntArray(size) { it }
        private val rank = IntArray(size) { 0 }

        fun find(i: Int): Int {
            if (parent[i] != i)
                parent[i] = find(parent[i])
            return parent[i]
        }

        fun union(i: Int, j: Int) {
            val rootI = find(i)
            val rootJ = find(j)
            if (rootI != rootJ) {
                if (rank[rootI] < rank[rootJ])
                    parent[rootI] = rootJ
                else if (rank[rootI] > rank[rootJ])
                    parent[rootJ] = rootI
                else {
                    parent[rootJ] = rootI
                    rank[rootI]++
                }
            }
        }
    }
}

