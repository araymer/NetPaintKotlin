package model

import java.awt.Point
import java.io.Serializable

class Line(override val color: String, override val startLocation: Point, val endLocation: Point) : Shape, Serializable