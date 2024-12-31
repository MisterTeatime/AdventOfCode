package de.werner.adventofcode.common

fun List<String>.at(point: Point2D): Char = this[point.y][point.x]

fun List<String>.isOnMap(point: Point2D): Boolean =
    point.x in this[0].indices && point.y in this.indices