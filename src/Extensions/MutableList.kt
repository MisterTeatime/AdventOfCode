import java.util.*

/**
 * Verschiebt das angegebenen **T** Objekt an den angegebenen neuen Index
 */
fun <T> MutableList<T>.move(item: T, newIndex: Int) {
    val currentIndex = indexOf(item)
    if (currentIndex < 0) return
    removeAt(currentIndex)
    add(newIndex, item)
}

/**
 * Verschiebt das Objekt an `oldIndex` nach `newIndex`
 */
fun <T> MutableList<T>.moveAt(oldIndex: Int, newIndex: Int) {
    val item = this[oldIndex]
    removeAt(oldIndex)
    add(newIndex, item)
}

/**
 * Verschiebt alle Elemente, die die Bedingungen erfüllen an den angegebenen Index
 */
fun <T> MutableList<T>.moveAll(newIndex: Int, predicate: (T) -> Boolean) {
    check(newIndex in 0 until size)

    val split = partition(predicate)
    clear()
    addAll(split.second)
    addAll(if (newIndex >= size) size else newIndex, split.first)
}

/**
 * Verschiebt das Element am angegebenen Index in der **MutableList** um einen Schritt nach vorne.
 * Wenn der Anfang der Liste erreicht ist, findet keine Verschiebung statt.
 */
fun <T> MutableList<T>.moveUpAt(index: Int) {
    if (index == 0) return
    if (index < 0 || index >= size) throw Exception("Index $index ist ungültig für eine Liste der Größe $size")

    val newIndex = index - 1
    val item = this[index]
    removeAt(index)
    add(newIndex, item)
}

/**
 * Verschiebt das Element am angegebenen Index in der **MutableList** um einen Schritt nach hinten.
 * Wenn das Ende der Liste erreicht ist, findet keine Verschiebung statt.
 */
fun <T> MutableList<T>.moveDownAt(index: Int) {
    if (index == size - 1) return
    if (index < 0 || index >= size) throw Exception("Index $index ist ungültig für eine Liste der Größe $size")

    val newIndex = index + 1
    val item = this[index]
    removeAt(index)
    add(newIndex, item)
}

/**
 * Verschiebt das angegebene Element der **MutableList** um einen Schritt nach vorne, außer es ist
 * bereits am Anfang der Liste. In diesem Fall findet keine Verschiebung statt.
 * Gibt ein `Boolean` zurück, das den Erfolg der Verschiebung anzeigt.
 */
fun <T> MutableList<T>.moveUp(item: T): Boolean {
    val currentIndex = indexOf(item)
    if (currentIndex == -1) return false

    val newIndex = currentIndex - 1
    if (newIndex <= 0) return false

    remove(item)
    add(newIndex, item)
    return true
}

/**
 * Verschiebt das angegebene Element der **MutableList** um einen Schritt nach hinten, außer es ist
 * bereits am Ende der Liste. In diesem Fall findet keine Verschiebung statt.
 * Gibt ein `Boolean` zurück, das den Erfolg der Verschiebung anzeigt.
 */
fun <T> MutableList<T>.moveDown(item: T): Boolean {
    val currentIndex = indexOf(item)
    if (currentIndex == -1) return false

    val newIndex = currentIndex + 1
    if (newIndex >= size) return false

    remove(item)
    add(newIndex, item)
    return true
}

/**
 * Tauscht die Objekte an den beiden angegebenen Positionen
 */
fun <T> MutableList<T>.swap(indexOne: Int, indexTwo: Int) {
    Collections.swap(this, indexOne, indexTwo)
}

/**
 * Tauscht die beiden angegebenen Objekte innerhalb der Liste
 */
fun <T> MutableList<T>.swap(itemOne: T, itemTwo: T) = swap(indexOf(itemOne), indexOf(itemTwo))
