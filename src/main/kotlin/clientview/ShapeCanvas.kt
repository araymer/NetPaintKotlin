package clientview

import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.input.*
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Ellipse
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape
import tornadofx.*
import java.util.*
import kotlin.math.abs

var shapeCanvas: ShapeCanvas? = null

class ShapeCanvas : View("Drawing Canvas") {
    var layers = mutableListOf(LinkedList<Shape>())
    var selectedShape: Shape? = null
    var creatingShape: Shape? = null
    var creatingCenter: Point2D? = null
    var selectedTool: TOOL = TOOL.SELECT
    var activeLayer = layers[0]
    var selectedOffset: Point2D? = null


    init {
        shapeCanvas = this
    }

    fun Shape.select() {
        this.stroke = Color.RED
    }

    fun Shape.deselect() {
        this.stroke = null
    }

    override val root = stackpane {
        useMaxWidth = true
        useMaxHeight = true
        style {
            backgroundColor += Color.WHITESMOKE
        }

        layers.forEach {
            for (shape in it) {
                add(shape)
            }
        }

        fun setOffset(evt: MouseEvent) {
            val mp = this.parent.sceneToLocal(evt.sceneX, evt.sceneY)
            val vizBounds = this.boundsInParent

            selectedOffset = Point2D(
                mp.x - vizBounds.minX - (vizBounds.width - this.boundsInLocal.width) / 2,
                mp.y - vizBounds.minY - (vizBounds.height - this.boundsInLocal.height) / 2
            )
        }


        fun selectOrCreate(evt: MouseEvent) {
            setOffset(evt)
            if (selectedTool == TOOL.SELECT) {
                activeLayer.filterNotNull()
                    .firstOrNull {
                        val mousePt = it.sceneToLocal(evt.sceneX, evt.sceneY)
                        it.contains(mousePt)
                    }.apply {
                        if (this != null) {
                            selectedShape = this
                            layers.forEach { for (shape in it) shape.deselect() }
                            this.select()
                        } else {
                            layers.forEach { for (shape in it) shape.deselect() }
                        }
                    }
            } else {


                when (selectedTool) {
                    TOOL.CIRCLE -> {
                        creatingShape = ellipse {
                            centerX = evt.sceneX
                            centerY = evt.sceneY
                            radiusX = 1.0
                            radiusY = 1.0
                            fill = selectedColor
                            isFocusTraversable = true
                        }
                    }
                    TOOL.RECTANGLE -> {
                        creatingShape = rectangle { }
                    }
                    TOOL.LINE -> {
                        creatingShape = line { }
                    }
                    TOOL.TRIANGLE -> {

                    }
                    TOOL.SQUIGGLE -> {

                    }
                }
                creatingShape?.let {
                    add(it)
                    creatingCenter = (evt.source as Pane).sceneToLocal( evt.sceneX, evt.sceneY )
                    println("creating center = $creatingCenter")
                }
            }
        }

        //TODO: There's a bug somewhere in the code below that's causing the shape to snap back to center when moving it, before you can then move it elsewhere.
        fun createOrMoveShape(evt: MouseEvent) {

            if (creatingShape != null) {
                setOffset(evt)
                when (creatingShape) {
                    is Ellipse -> {
                        getChildList()?.remove(creatingShape as Ellipse)
                        creatingShape = ellipse {
                            centerX = creatingCenter?.x!!
                            centerY = creatingCenter?.y!!
                            radiusX = abs(creatingCenter?.x?.minus(selectedOffset?.x!!) ?: 1.0)
                            radiusY = abs(creatingCenter?.y?.minus(selectedOffset?.y!!) ?: 1.0)
                            fill = selectedColor
                            isFocusTraversable = true
                        }
                        add(creatingShape as Ellipse)
                    }
                    is Rectangle -> {

                    }
                    TOOL.LINE -> {

                    }
                    TOOL.TRIANGLE -> {

                    }
                    TOOL.SQUIGGLE -> {

                    }
                }

            } else {
                val mousePt : Point2D = (evt.source as Pane).sceneToLocal( evt.sceneX, evt.sceneY )
                println("$mousePt -- $selectedOffset")
                selectedShape?.translateX = mousePt.x - selectedOffset?.x!!
                selectedShape?.translateY = mousePt.y - selectedOffset?.y!!
            }
        }

        fun finishCreateOrMoveShape(evt: MouseEvent) {
            if (selectedTool != TOOL.SELECT) {
                activeLayer.push(creatingShape)

            }
            creatingShape = null
            selectedOffset = null
            selectedShape?.deselect()
            selectedShape = null
            creatingCenter = null
        }

        addEventFilter(MouseEvent.MOUSE_PRESSED, ::selectOrCreate)
        addEventFilter(MouseEvent.MOUSE_DRAGGED, ::createOrMoveShape)
        addEventFilter(MouseEvent.MOUSE_RELEASED, ::finishCreateOrMoveShape)
        addEventFilter(KeyEvent.KEY_PRESSED, object : EventHandler<KeyEvent?> {
            val undo: KeyCombination = KeyCodeCombination(
                KeyCode.Z,
                KeyCombination.SHORTCUT_DOWN
            )
            val redo: KeyCombination = KeyCodeCombination(
                KeyCode.Z,
                KeyCombination.SHIFT_DOWN,
                KeyCombination.SHORTCUT_DOWN
            )

            override fun handle(ke: KeyEvent?) {
                if (undo.match(ke)) {
                    // TODO: SEND UNDO MESSAGE
                    ke?.consume()
                } else if (redo.match(ke)) {
                    // TODO: SEND REDO MESSAGE
                }
            }
        })
    }
}