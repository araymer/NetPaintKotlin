package clientview

import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.input.*
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Shape
import tornadofx.*
import java.util.*

var shapeCanvas: ShapeCanvas? = null

class ShapeCanvas : View("Drawing Canvas") {
    var layers = mutableListOf(LinkedList<Shape>())
    var selectedShape: Shape? = null
    var selectedTool: TOOL = TOOL.SELECT
    var activeLayer = layers[0]
    var isCreatingShape = false
    var selectedOffset: Point2D? = null


    init {
        shapeCanvas = this
    }

    override val root = stackpane {
        useMaxWidth = true
        useMaxHeight = true
        style {
            backgroundColor += Color.WHITESMOKE
        }



        fun selectOrCreate(evt: MouseEvent) {
            val x = evt.sceneX
            val y = evt.sceneY

            if(selectedTool == TOOL.SELECT) {
                activeLayer.filterNotNull()
                    .firstOrNull {
                        val mousePt = it.sceneToLocal(evt.sceneX, evt.sceneY)
                        it.contains(mousePt)
                    }.apply {
                        if( this != null ) {

                            selectedShape = this

                            val mp = this.parent.sceneToLocal(evt.sceneX, evt.sceneY)
                            val vizBounds = this.boundsInParent

                            selectedOffset = Point2D(
                                mp.x - vizBounds.minX - (vizBounds.width - this.boundsInLocal.width) / 2,
                                mp.y - vizBounds.minY - (vizBounds.height - this.boundsInLocal.height) / 2
                            )
                        }
                    }
            } else {
                isCreatingShape = true
                // TODO: draw ephemeral shape
            }
        }

        fun createOrMoveShape(evt: MouseEvent) {
            if (isCreatingShape) {
                when (selectedTool) {
                    TOOL.CIRCLE -> {

                    }
                    TOOL.RECTANGLE -> {

                    }
                    TOOL.LINE -> {

                    }
                    TOOL.TRIANGLE -> {

                    }
                    TOOL.SQUIGGLE -> {

                    }
                }
            } else {
                val mousePt: Point2D = (evt.source as Pane).sceneToLocal(evt.sceneX, evt.sceneY)
                if (selectedOffset != null) {

                    selectedShape?.relocate(
                        mousePt.x - selectedOffset!!.x,
                        mousePt.y - selectedOffset!!.y
                    )
                }
            }
        }

        fun finishCreateOrMoveShape(evt: MouseEvent) {
            isCreatingShape = false
            if(selectedTool != TOOL.SELECT) {
                // TODO: Commit shape
            }
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