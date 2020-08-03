package model

import java.awt.Point
import java.io.Serializable

class Triangle(
    override val color: String,
    override val startLocation: Point,
    val point2: Point,
    val point3: Point
) : Shape, Serializable