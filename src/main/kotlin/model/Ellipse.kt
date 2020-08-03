package model

import java.awt.Point
import java.io.Serializable

class Ellipse(
    override val startLocation: Point,
    override val color: String,
    val xRadius: Int,
    val yRadius: Int
): Shape, Serializable