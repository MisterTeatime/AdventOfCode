package de.werner.adventofcode2024

import readInput

class Day24 {
    private val testInput = readInput("Day24_test")
    private val input = readInput("Day24")

    fun solvePart1(input: List<String> = this.input): Long {
        val network = getLogicNetwork(input)
        val output = network.evaluate()

        println(output)

        return output.foldIndexed(0) {index, acc, bit ->
            if (bit) acc + (1L shl (index)) else acc
        }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Int = input.size

    fun testPart2() = solvePart2(testInput)

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
                    "OR" -> LogicGate.OrGate
                    "AND" -> LogicGate.AndGate
                    "XOR" -> LogicGate.XorGate
                    else -> throw IllegalArgumentException("$gateName ist kein zulässiger Gatetyp")
                }
                output to Triple(gate, firstIn, secondIn)
            }

        return LogicNetwork(gates, externalInputs)
    }
}

sealed class LogicGate {
    abstract fun evaluate(input1: Boolean, input2: Boolean): Boolean

    data object AndGate : LogicGate() {
        override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 && input2
    }

    data object OrGate : LogicGate() {
        override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 || input2
    }

    data object XorGate : LogicGate() {
        override fun evaluate(input1: Boolean, input2: Boolean): Boolean = input1 != input2
    }
}

class LogicNetwork(
    private val gates: Map<String, Triple<LogicGate, String, String>>, // Name -> (Gate, Input1, Input2)
    private val externalInputs: Map<String, Boolean> // Externe Eingaben
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
}

