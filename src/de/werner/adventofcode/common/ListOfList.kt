package de.werner.adventofcode.common

fun <T> List<List<T>>.getRows(): List<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>> {
    val sequences = mutableListOf<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>>()
    val numRows = this.size

    for (i in 0 ..< numRows) {
        sequences.add(Triple(this[i], i to 0, 0 to 1))
    }

    return sequences
}

fun List<String>.getStringRows(): List<Triple<String, Pair<Int, Int>, Pair<Int, Int>>> =
    this.map { it.toList() }
        .getRows()
        .map { Triple(it.first.joinToString(""), it.second, it.third)}

fun <T> List<List<T>>.getColumns(): List<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>> {
    val sequences = mutableListOf<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>>()
    val numRows = this.size
    val numCols = this[0].size

    for (j in 0 ..< numCols) {
        val col = mutableListOf<T>()

        for (i in 0 ..< numRows) {
            col.add(this[i][j])
        }
        sequences.add(Triple(col, 0 to j, 1 to 0))
    }

    return sequences
}

fun List<String>.getStringColumns(): List<Triple<String, Pair<Int, Int>, Pair<Int, Int>>> =
    this.map { it.toList() }
        .getColumns()
        .map { Triple(it.first.joinToString(""), it.second, it.third)}

fun <T> List<List<T>>.getNWSODiagonals(wordLength: Int): List<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>> {
    val sequences = mutableListOf<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>>()
    val numRows = this.size
    val numCols = this[0].size

    for (d in -(numRows - 1)..< numCols) {
        val diagonal = mutableListOf<T>()
        var startRow = -1
        var startCol = -1

        for (i in 0 ..< numRows) {
            val j = i + d
            if (j in 0 ..< numCols) {
                if (startRow == -1) {
                    startRow = i
                    startCol = j
                }
                diagonal.add(this[i][j])
            }
        }
        if (diagonal.size >= wordLength) {
            sequences.add(Triple(diagonal, startRow to startCol, 1 to -1))
        }
    }

    return sequences
}

fun List<String>.getStringNWSODiagonals(wordLength: Int): List<Triple<String, Pair<Int, Int>, Pair<Int, Int>>> =
    this.map { it.toList() }
        .getNWSODiagonals(wordLength)
        .map { Triple(it.first.joinToString(""), it.second, it.third)}

fun <T> List<List<T>>.getNOSWDiagonals(wordLength: Int): List<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>> {
    val sequences = mutableListOf<Triple<List<T>, Pair<Int, Int>, Pair<Int, Int>>>()
    val numRows = this.size
    val numCols = this[0].size

    for (d in 0 ..< numRows + numCols - 1) {
        val diagonal = mutableListOf<T>()
        var startRow = -1
        var startCol = -1

        for (i in 0 ..< numRows) {
            val j = d - i
            if (j in 0 ..< numCols) {
                if (startRow == -1) {
                    startRow = i
                    startCol = j
                }
                diagonal.add(this[i][j])
            }
        }
        if (diagonal.size >= wordLength) {
            sequences.add(Triple(diagonal, startRow to startCol, 1 to -1))
        }
    }

    return sequences
}

fun List<String>.getStringNOSWDiagonals(wordLength: Int): List<Triple<String, Pair<Int, Int>, Pair<Int, Int>>> =
    this.map { it.toList() }
        .getNOSWDiagonals(wordLength)
        .map { Triple(it.first.joinToString(""), it.second, it.third)}