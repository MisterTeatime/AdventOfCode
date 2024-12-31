package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*
import java.util.*

class Day12 {
    private val testInput = readInput("""2024\Day12_test""")
    private val input = readInput("""2024\Day12""")

    fun solvePart1(input: List<String> = this.input): Long = timing { calculateCosts(input) }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = timing { input.size }

    fun testPart2() = solvePart2(testInput)

    data class Bereich(val art: Char, var area: Long = 0L, var umfang: Long = 0L, val id: UUID = UUID.randomUUID() )

    private fun calculateCosts(map: List<String>): Long {
        val height = map.size
        val width = map[0].length

        val cellToArea = mutableMapOf<Point2D, Bereich>()
        val bereiche = mutableListOf<Bereich>()

        val richtungen = listOf(
            Point2D(-1,0),
            Point2D(1,0),
            Point2D(0,-1),
            Point2D(0,1)
        )

        fun isValid(p: Point2D): Boolean =
            p.x in 0 until width && p.y in 0 until height

        fun bereichFindenOderErstellen(art: Char, punkt: Point2D): Bereich {
            val angrenzendeBereiche = richtungen
                .asSequence()
                .map { punkt + it }
                .filter { isValid(it) }
                .mapNotNull { cellToArea[it] }
                .distinct()
                .filter { it.art == art}
                .toList()

            val richtungToPoints = richtungen.map { punkt + it }
            val filteredPoints = richtungToPoints.filter { isValid(it) }
            val pointsToArea = filteredPoints.mapNotNull { cellToArea[it] }
            val distinctAreas = pointsToArea.distinct()
            val filteredAreas = distinctAreas.filter { it.art == art }.toList()

            return if (angrenzendeBereiche.isEmpty()) {
                val neuerBereich = Bereich(art)
                bereiche.add(neuerBereich)
                neuerBereich
            } else {
                val hauptBereich = angrenzendeBereiche.first()
                for (bereich in angrenzendeBereiche.drop(1)) {
                    hauptBereich.area += bereich.area
                    hauptBereich.umfang += bereich.umfang
                    for ((zelle, b) in cellToArea.entries) {
                        if (b == bereich) cellToArea[zelle] = hauptBereich
                    }
                    bereiche.remove(bereich)
                }
                hauptBereich
            }
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val punkt = Point2D(x, y)
                if (punkt in cellToArea) continue

                val art = map[y][x]
                val bereich = bereichFindenOderErstellen(art, punkt)

                cellToArea[punkt] = bereich
                bereich.area++
                bereich.umfang += 4

                for (richtung in richtungen) {
                    // es dürfen nur Nachbarn geprüft werden, die bereits besucht wurden
                    val nachbar = punkt + richtung
                    if (isValid(nachbar) && map[nachbar.y][nachbar.x] == art && cellToArea[nachbar] != null) {
                        bereich.umfang -= 2
                        //bereich.area++
                    }
                }

            }
        }
        return bereiche.sumOf { it.area * it.umfang }
    }
}

