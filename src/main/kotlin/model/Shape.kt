package model

import java.awt.Point
import java.io.Serializable

interface Shape: Serializable {
    val color: String
    val startLocation: Point
}