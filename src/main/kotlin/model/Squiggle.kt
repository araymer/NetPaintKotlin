package model

import java.awt.Point
import java.io.Serializable
import java.util.*

//TODO: Need to figure out how to calculate a curve to allow a "free hand" sort of drawing feel.
class Squiggle(override val startLocation: Point, override val color: String, val width: Int): Shape, Serializable {
    val pixels: LinkedList<Point> = LinkedList()
}