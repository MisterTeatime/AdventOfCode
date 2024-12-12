fun main() {
    fun part1(input: List<String>): Long {
        return calculateCosts(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    val resultPart1 = part1(testInput)
    println("Test Part 1: $resultPart1")
    check(resultPart1 == 140L)

//    val resultPart2 = part2(testInput)
//    println("Test Part 2: $resultPart2")
//    check(resultPart2 == 1)

    val input = readInput("Day12")
    println("Part 1: ${part1(input)}")
//    println("Part 2: ${part2(input)}")
}

data class Bereich(val art: Char, var area: Long = 0L, var umfang: Long = 0L)

fun calculateCosts(map: List<String>): Long {
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
            .map { punkt + it }
            .filter { isValid(it) }
            .mapNotNull { cellToArea[it] }
            .distinct()
            .filter { it.art == art}

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

    for (x in 0 until width) {
        for (y in 0 until height) {
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