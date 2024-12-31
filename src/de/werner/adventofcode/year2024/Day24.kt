package de.werner.adventofcode.year2024

import de.werner.adventofcode.common.*

class Day24 {
    private val testInput = readInput("""2024\Day24_test""")
    private val input = readInput("""2024\Day24""")

    fun solvePart1(input: List<String> = this.input): Long = timing {
        val network = getLogicNetwork(input)
        val output = network.evaluate()

        println(output)

        return@timing binaryListToLong(output)
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): String {
        val network = getLogicNetwork(input)
        val inputX = binaryListToLong(network.externalInputs
            .filterKeys { it.startsWith("x") }
            .values.toList()
        )
        val inputY = binaryListToLong(network.externalInputs
            .filterKeys { it.startsWith("y") }
            .values.toList()
        )

        val output = longToBinaryList(inputX + inputY)
        return ""
    }

    fun testPart2() = solvePart2(testInput)

    private fun binaryListToLong(input: List<Boolean>): Long =
        input.foldIndexed(0) {index, acc, bit ->
            if (bit) acc + (1L shl (index)) else acc
        }

    private fun longToBinaryList(number: Long): List<Boolean> {
        var num = number
        val binaryList = mutableListOf<Boolean>()

        while (num > 0) {
            binaryList.add(num and 1 == 1L)
            num = num shr 1
        }

        return binaryList
    }

    private fun getLogicNetwork(input: List<String>): LogicNetwork {
        val index = input.indexOf("")
        require(index > 0) { "Input not correct" }

        val externalInputs = input.take(index)
            .associate {
                val (name, value) = it.split(": ")
                name to (value.toInt() != 0)
            }
        val gates = input.drop(index + 1)
            .associate {
                val (firstIn, gateName, secondIn, _, output) = it.split(" ")
                val gate = when(gateName) {
                    "OR" -> LogicGate.OR
                    "AND" -> LogicGate.AND
                    "XOR" -> LogicGate.XOR
                    else -> throw IllegalArgumentException("$gateName ist kein zulässiger Gatetyp")
                }
                output to Triple(gate, firstIn, secondIn)
            }

        return LogicNetwork(gates, externalInputs)
    }

//    sealed class LogicGate {
//        abstract fun evaluate(input1: Boolean, input2: Boolean): Boolean
//
//        data object AndGate : LogicGate() {
//            override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 && input2
//        }
//
//        data object OrGate : LogicGate() {
//            override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 || input2
//        }
//
//        data object XorGate : LogicGate() {
//            override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 != input2
//        }
//    }
        enum class LogicGate {
            OR, XOR, AND;

        fun evaluate(input1: Boolean, input2: Boolean): Boolean = when (this) {
            OR -> input1 || input2
            AND -> input1 && input2
            XOR -> input1 != input2
        }
        }

    class LogicNetwork(
        private val gates: Map<String, Triple<LogicGate, String, String>>, // Name -> (Gate, Input1, Input2)
        val externalInputs: Map<String, Boolean> // Externe Eingaben
    ) {
        private val computedOutputs = mutableMapOf<String, Boolean>() // Cache für berechnete Outputs

        fun evaluate(): List<Boolean> {
            // Filter und sortiere Outputs
            val outputMapping = gates.keys.filter { it.startsWith("z") }.sorted()

            // Berechne alle Outputs in der sortierten Reihenfolge
            return outputMapping.map { outputName ->
                computeGateOutput(outputName)
            }
        }

        private fun computeGateOutput(gateName: String): Boolean {
            // Falls der Output bereits berechnet wurde, direkt zurückgeben
            computedOutputs[gateName]?.let { return it }

            // Falls der Name in externen Eingaben liegt, direkt zurückgeben
            externalInputs[gateName]?.let { return it }

            // Gate-Definition abrufen
            val (gate, input1Name, input2Name) = gates[gateName]
                ?: error("Gate $gateName not found")

            // Inputs rekursiv berechnen
            val input1 = computeGateOutput(input1Name)
            val input2 = computeGateOutput(input2Name)

            // Gate evaluieren und Ergebnis cachen
            val output = gate.evaluate(input1, input2)
            computedOutputs[gateName] = output
            return output
        }

        fun findSwappedWires(
            expectedInputs: Map<String, Pair<String, String>> // z -> (expected x or y pair)
        ): List<String> {
            val swappedWires = mutableSetOf<String>()

            expectedInputs.forEach { (zOutput, expectedPair) ->
                // Rückverfolgung der tatsächlichen Inputs
                val actualInputs = traceBackInputs(zOutput)

                // Vergleiche erwartete und tatsächliche Inputs
                if (actualInputs != expectedPair) {
                    swappedWires.addAll(listOf(actualInputs.first, actualInputs.second, expectedPair.first, expectedPair.second))
                }
            }

            return swappedWires.sorted()
        }

        private fun traceBackInputs(output: String): Pair<String, String> {
            val gate = gates[output]
                ?: error("Gate $output not found in logic network")

            val input1 = gate.second
            val input2 = gate.third

            // Falls die Inputs keine Gates mehr sind, direkt zurückgeben
            if (!gates.containsKey(input1) && !gates.containsKey(input2)) {
                return Pair(input1, input2)
            }

            // Ansonsten rekursiv zurückverfolgen
            val tracedInput1 = if (gates.containsKey(input1)) traceBackInputs(input1).first else input1
            val tracedInput2 = if (gates.containsKey(input2)) traceBackInputs(input2).second else input2

            return Pair(tracedInput1, tracedInput2)
        }

    }
}



