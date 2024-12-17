package Extensions

import Point2D

fun List<String>.at(point: Point2D): Char = this[point.y][point.x]

fun List<String>.isOnMap(point: Point2D): Boolean =
    point.x in this[0].indices && point.y in this.indices