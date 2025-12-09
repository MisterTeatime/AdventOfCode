package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*
import java.util.PriorityQueue

class Day08 {
    private val testInput = readInput("""2025\Day08_test""")
    private val input = readInput("""2025\Day08""")

    fun solvePart1(input: List<String> = this.input, n: Int = 1000): Int = timing {
        val points = parsePoints(input)
        val edges = topNEdges(points, n)
        val uf = UnionFind(points.size)
        var connections = 0

        for (edge in edges) {
            if (uf.union(edge.p1, edge.p2)) {
                connections++
            }
        }

        val componentSizes = mutableMapOf<Int, Int>()
        for (i in 0 until points.size) {
            val root = uf.find(i)
            componentSizes[root] = (componentSizes[root] ?: 0) + 1
        }

        val top3 = componentSizes.values.sortedDescending().take(3)
        top3.fold(1) { acc, size -> acc * size }
    }

    fun testPart1() = solvePart1(testInput, 10)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val points = parsePoints(input)
        val edges = allSortedEdges(points)
        val uf = UnionFind(points.size)
        var connections = 0
        var lastEdge: Edge? = null

        for (edge in edges) {
            if (uf.union(edge.p1, edge.p2)) {
                connections++
                lastEdge = edge
            }
            if (connections == points.size - 1) break
        }

        val (box1, box2, _) = lastEdge!!
        println("Last edge: ${points[box1]} -> ${points[box2]}")
        points[box1].x.toLong() * points[box2].x.toLong()
    }

    fun testPart2() = solvePart2(testInput)

    fun parsePoints(input: List<String>): List<Point3D> {
        return input.map { line ->
            val (x, y, z) = line.split(",").map { it.toInt() }
            Point3D(x, y, z)
        }
    }

    fun topNEdges(points: List<Point3D>, n: Int): List<Edge> {
        val edges = PriorityQueue<Edge>(compareBy<Edge> { it.distance }.reversed())

        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val distance = points[i].distanceTo(points[j])
                val edge = Edge(i, j, distance)

                if (edges.size < n) {
                    edges.offer(edge)
                } else if (distance < edges.peek().distance) {
                    edges.poll()
                    edges.offer(edge)
                }
            }
        }

        return edges.toList().sortedBy { it.distance }
    }

    fun allSortedEdges(points: List<Point3D>): List<Edge> = (0 until points.size).flatMap { i ->
        (i + 1 until points.size).map { j ->
            Edge(i, j, points[i].distanceTo(points[j]))
        }
    }.sortedBy { it.distance }
}

class UnionFind(private val n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x])
        }
        return parent[x]
    }

    fun union(x: Int, y: Int): Boolean {
        val rootX = find(x)
        val rootY = find(y)

        if (rootX == rootY) return false

        if (size[rootX] < size[rootY]) {
            parent[rootX] = rootY
            size[rootY] += size[rootX]
        } else {
            parent[rootY] = rootX
            size[rootX] += size[rootY]
        }
        return true
    }
}

data class Edge(val p1: Int, val p2: Int, val distance: Double) {
    override fun toString(): String = "$p1 -> $p2 ($distance)"
}

