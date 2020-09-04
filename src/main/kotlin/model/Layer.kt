package model

import java.io.Serializable
import java.util.*

class Layer: Serializable {
    var drawnShapes: LinkedList<Shape> = LinkedList()
    private var undoneShapes: LinkedList<Shape> = LinkedList()

    //TODO: These methods don't handle end-edge-cases
    fun addShape(shape: Shape?) {
        shape?.let { drawnShapes.push(it) }
    }

    fun undo() {
        if(drawnShapes.size > 0) undoneShapes.push(drawnShapes.pop())
    }

    fun redo() {
        if(undoneShapes.size > 0) drawnShapes.push(undoneShapes.pop())
    }
}