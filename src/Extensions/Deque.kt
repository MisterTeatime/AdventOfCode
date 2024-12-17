package Extensions

fun <T> ArrayDeque<T>.push(item: T) = this.addFirst(item)
fun <T> ArrayDeque<T>.pop(): T = this.removeFirst()
fun <T> ArrayDeque<T>.peek(): T = this.first()