fun findWord(grid: List<String>, word: String): List<Pair<Int, Int>> {
    val reversedWord = word.reversed()
    val targetWords = listOf(word, reversedWord)
    val wordLength = word.length
    val sequences = mutableListOf<Triple<String, Pair<Int, Int>, Pair<Int, Int>>>()

    val numRows = grid.size
    val numCols = grid[0].length

    // Horizontale Sequenzen
    for (i in 0 until numRows) {
        sequences.add(Triple(grid[i], i to 0, 0 to 1))
    }

    // Vertikale Sequenzen
    for (j in 0 until numCols) {
        val col = StringBuilder()
        for (i in 0 until numRows) {
            col.append(grid[i][j])
        }
        sequences.add(Triple(col.toString(), 0 to j, 1 to 0))
    }

    // Diagonale Sequenzen (↘)
    for (d in -(numRows - 1)..(numCols - 1)) {
        val diagonal = StringBuilder()
        var startRow = -1
        var startCol = -1
        for (i in 0 until numRows) {
            val j = i + d
            if (j in 0 until numCols) {
                if (startRow == -1) {
                    startRow = i
                    startCol = j
                }
                diagonal.append(grid[i][j])
            }
        }
        if (diagonal.length >= wordLength) {
            sequences.add(Triple(diagonal.toString(), startRow to startCol, 1 to 1))
        }
    }

    // Diagonale Sequenzen (↙)
    for (d in 0 until numRows + numCols - 1) {
        val diagonal = StringBuilder()
        var startRow = -1
        var startCol = -1
        for (i in 0 until numRows) {
            val j = d - i
            if (j in 0 until numCols) {
                if (startRow == -1) {
                    startRow = i
                    startCol = j
                }
                diagonal.append(grid[i][j])
            }
        }
        if (diagonal.length >= wordLength) {
            sequences.add(Triple(diagonal.toString(), startRow to startCol, 1 to -1))
        }
    }

    // Suche nach Wörtern in den Sequenzen
    val results = mutableListOf<Pair<Int, Int>>()
    for ((sequence, startPos, direction) in sequences) {
        for (w in targetWords) {
            var index = sequence.indexOf(w)
            while (index != -1) {
                // Berechne Startposition des Wortes
                val startRow = startPos.first + index * direction.first
                val startCol = startPos.second + index * direction.second
                results.add(startRow to startCol)
                index = sequence.indexOf(w, index + 1)
            }
        }
    }

    return results
}
