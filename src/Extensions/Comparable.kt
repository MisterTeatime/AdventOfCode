/**
 * Sucht aus [collection] die [n] größten Elemente und gibt sie als Liste zurück.
 */
fun <T: Comparable<T>> topMax(n: Int, collection: Iterable<T>): List<T> {
    return collection.fold(mutableListOf<T>()) { topList, candidate ->
        if (topList.size < n || candidate > topList.last()) {
            topList.add(candidate)
            topList.sortDescending()
            if (topList.size > n)
                topList.removeAt(n)
        }
        topList
    }
}

/**
 * Sucht aus [collection] die [n] kleinsten Elemente und gibt sie als Liste zurück.
 */
fun <T: Comparable<T>> topMin(n: Int, collection: Iterable<T>): List<T> {
    return collection.fold(mutableListOf<T>()) { topList, candidate ->
        if (topList.size < n || candidate < topList.last()) {
            topList.add(candidate)
            topList.sort()
            if (topList.size > n)
                topList.removeAt(n)
        }
        topList
    }
}