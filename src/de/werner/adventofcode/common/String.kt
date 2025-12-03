package de.werner.adventofcode.common

const val ANSI_RESET = "\u001B[0m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"

fun String.red() = "$ANSI_RED$this$ANSI_RESET"
fun String.green() = "$ANSI_GREEN$this$ANSI_RESET"
fun String.yellow() = "$ANSI_YELLOW$this$ANSI_RESET"
fun String.blue() = "$ANSI_BLUE$this$ANSI_RESET"