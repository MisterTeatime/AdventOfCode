import kotlin.math.pow

/**
 * Liest einen String als SNAFU-Zahl und gibt den Dezimalwert zurück.
 * Wird ein ungültiges Zeichen gefunden, wird eine Ausnahme geworfen.
 * Die Zeichen werden mit `fold()` zusammengefasst. Dabei wird jedes Zeichen
 * in den entsprechenden Int-Wert gewandelt und dann mit der Formel
 * `cValue * 5 ^ index` berechnet und zum Gesamtwert hinzuaddiert.
 *
 * Durch die Übersetzung der negativ Werte geht die Berechnung genau auf ohne sich
 * um die Carry Overs kümmern zu müssen.
 */
fun String.parseSNAFU(): Long {
    return this.reversed().foldIndexed(0) { index, acc, c ->
        val cValue = when (c) {
            '=' -> -2
            '-' -> -1
            '0' -> 0
            '1' -> 1
            '2' -> 2
            else -> throw NumberFormatException("Illegal SNAFU character")
        }
        (acc + cValue * 5.toDouble().pow(index)).toLong()
    }
}

/**
 * Wandelt eine Integerzahl in ihr SNAFU-Repräsentation um.
 * Ein Flag zeigt an, ob es bereits zu einem Carry Over gekommen ist. In diesem Fall
 * wird der Wert des aktuellen Zeichens um 1 erhöht. Dann wird das Carry-Over-Flag
 * angepasst. Für die eigentlich übersetzung wird dann den Werten 0 bis 5 ihr SNAFU-Wert
 * zugeordnet. Bei 0, 1 und 2 ist es direkt dieser Wert. 3 wird zu `=`, weil es 2 unter de
 * der nächsten 5er-Potenz liegt, 4 wird zu `-`, weil es 1 darunter liegt. Der Wert 5
 * muss extra behandelt werden, da er zu einer 0 mit Carry Over wird.
 * Nachdem die Zahl umgewandelt wurde, muss ggf. ein existierendes Carry Over behandelt
 * werden, in dem eine 1 angehängt wird.
 * Da die Dezimalzahl für die Umwandlung umgekehrt wurde, muss für die Ausgabe der
 * SNAFU-String nochmal umgekehrt werden.
 */
fun Long.toSNAFU(): String {
    var carry = false
    val result = this.toString(5).reversed().fold("") { acc, c ->
        val posVal = when (carry) {
            true -> c.digitToInt() + 1
            else -> c.digitToInt()
        }

        carry = posVal > 2

        when (posVal) {
            3 -> "$acc="
            4 -> "$acc-"
            5 -> "${acc}0"
            else -> "$acc${posVal.digitToChar()}"
        }
    }
    return when {
        carry -> "${result}1".reversed()
        else -> result.reversed()
    }
}