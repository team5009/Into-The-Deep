package ca.helios5009.hyperion.misc

import ca.helios5009.hyperion.misc.commands.Point
import kotlin.math.pow
import kotlin.math.sqrt

fun euclideanDistance(p1: Point, p2: Point): Double {
	return sqrt((p2.x - p1.x).pow(2.0) + (p2.y - p1.y).pow(2.0))
}

fun cubicBezier(p0: Double, p1: Double, p2: Double, p3: Double, t: Double): Double {
	return (1 - t).pow(3) * p0 + 3 * (1 - t).pow(2) * t * p1 + 3 * (1 - t) * t.pow(2) * p2 + t.pow(3) * p3
}

fun lerp(p0: Double, p1: Double, t: Double): Double {
	return p0 + t * (p1 - p0)
}