package de.werner.adventofcode.year2025

import de.werner.adventofcode.common.*

class Day10 {
    private val testInput = readInput("""2025\Day10_test""")
    private val input = readInput("""2025\Day10""")
    private val memo = mutableMapOf<String, Int>()


    fun solvePart1(input: List<String> = this.input): Long = timing {
        val machines = input.map { parseMachine(it) }
        machines.sumOf { machine ->
            val presses = minPresses(machine.targetMask, machine.switches)
            presses.toLong()
        }
    }

    fun testPart1() = solvePart1(testInput)

    fun solvePart2(input: List<String> = this.input): Long = timing {
        val machines = input.map { parseMachine(it)}
        machines.sumOf { machine ->
            val switchEffects = machine.switches
                .map { maskToEffect(it, machine.jolts.size) }
            solveGreedy(machine.jolts, switchEffects).toLong()
        }

//        memo.clear()
//        val machines = input.map { parseMachine(it) }
//
//        var total = 0L
//        machines.forEachIndexed { index, machine ->
//            val start = System.currentTimeMillis()
//            val switchEffects = machine.switches
//                .map { maskToEffect(it, machine.jolts.size) }
//            val presses = minPressesDP(machine.jolts, switchEffects)
//            val time = System.currentTimeMillis() - start
//            println("Machine $index: $presses presses, ${time}ms")
//            total += presses.toLong()
//        }
//        total
////        machines.sumOf { machine ->
////            val switchEffects = machine.switches
////                .map { maskToEffect(it, machine.jolts.size) }
////            minPressesDP(machine.jolts, switchEffects).toLong()
////        }
    }

    fun testPart2() = solvePart2(testInput)

    fun solveGreedy(jolts: IntArray, switchEffects: List<IntArray>): Int {
        val remaining = jolts.copyOf()
        var presses = 0

        while (remaining.any { it > 0 }) {
            var bestSwitch = -1
            var bestReduction = -1

            for ((index, effect) in switchEffects.withIndex()) {
                if (effect.zip(remaining).all { (e,r) -> e == 0 || e <= r }) {
                    val reduction = effect.zip(remaining).sumOf { (e,r) -> minOf(e,r) }
                    if (reduction > bestReduction) {
                        bestReduction = reduction
                        bestSwitch = index
                    }
                }
            }

            if (bestSwitch == -1) {
                println("Greedy failed - no feasible switch found")
                return -1
            }

            val effect = switchEffects[bestSwitch]
            for (i in remaining.indices) {
                remaining[i] -= effect[i]
            }
            presses++
        }

        return presses
    }

    fun minPresses(target: Long, switches: List<Long>): Int {
        val visited = mutableSetOf<Long>()
        val queue = ArrayDeque<Pair<Long, Int>>()

        queue.add(0L to 0)
        visited.add(0L)

        while (queue.isNotEmpty()) {
            val (state, presses) = queue.removeFirst()
            if (state == target) return presses

            for (sw in switches) {
                val newState = state xor sw
                if (newState !in visited) {
                    visited.add(newState)
                    queue.add(newState to presses + 1)
                }
            }
        }
        return -1
    }

    fun minPressesDP(jolts: IntArray, switchEffects: List<IntArray>): Int {
        val key = jolts.contentToString()
        if (key in memo) return memo[key]!!

        if (jolts.all { it == 0 }) {
            memo[key] = 0
            return 0
        }

        var minPresses = Int.MAX_VALUE

        for (effect in switchEffects) {
            if (isFeasible(effect, jolts)) {
                val nextState = subtract(effect, jolts)
                val presses = minPressesDP(nextState, switchEffects)
                if (presses != -1) {
                    minPresses = minOf(minPresses, presses + 1)
                }
            }
        }
        memo[key] = if (minPresses == Int.MAX_VALUE) -1 else minPresses
        return memo[key]!!
    }

    fun isFeasible(effect: IntArray, remaining: IntArray): Boolean = effect
        .zip(remaining).all { (e, r) -> e == 0 || e <= r }

    fun subtract(effect: IntArray, remaining: IntArray): IntArray =
        IntArray(remaining.size) { i -> remaining[i] - effect[i] }

    fun maskToEffect(mask: Long, nLights: Int): IntArray {
        val effect = IntArray(nLights)

        for (i in 0 until nLights) {
            val bitPos = nLights - 1 - i
            effect[i] = if ((mask and (1L shl bitPos)) != 0L) 1 else 0
        }
        return effect
    }

    fun parseMachine(line: String): Machine {
        val lights = line.substringBefore(' ').drop(1).dropLast(1)
        val nLights = lights.length

        val targetMask = parseTarget(lights)

        val switchParts = line
            .substringAfter(' ')
            .substringBefore('{')
            .split(' ')
            .filter { it.isNotEmpty() }
        val switches = switchParts.map { part ->
            val indices = part
                .drop(1)
                .dropLast(1)
                .split(',')
                .map { it.toInt() }
            parseSwitch(indices, nLights)
        }

        val joltStr = line
            .substringAfter('{')
            .substringBefore('}')
        val jolts = if (joltStr.isNotEmpty()) {
            joltStr.split(',')
                .map { it.toInt() }
                .toIntArray()
        } else IntArray(0)

        return Machine(targetMask, switches, jolts)
    }

    fun parseTarget(lights: String): Long {
        val n = lights.length

        var mask = 0L
        for (i in lights.indices) {
            if (lights[i] == '#') {
                val bitPos = n - 1 -i
                mask = mask or (1L shl bitPos)
            }
        }
        return mask
    }

    fun parseSwitch(indices: List<Int>, nLights: Int): Long {
        var mask = 0L
        for (index in indices) {
            val bitPos = nLights - 1 - index
            mask = mask or (1L shl bitPos)
        }
        return mask
    }
}

data class Machine(
    val targetMask: Long,
    val switches: List<Long>,
    val jolts: IntArray
)

