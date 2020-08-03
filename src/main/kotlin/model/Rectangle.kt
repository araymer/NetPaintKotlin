package model

import java.awt.Point
import java.io.Serializable

class Rectangle(
    x: Int,
    y: Int,
    override val color: String,
    val width: Int,
    val height: Int
): Shape, Serializable {
    override val startLocation = Point(x, y)
}