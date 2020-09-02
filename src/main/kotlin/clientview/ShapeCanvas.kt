package clientview

import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import model.Layer
import model.Shape
import tornadofx.*

class ShapeCanvas : View("Drawing Canvas") {
    var layers = mutableListOf(Layer())
    var selectedShape: Shape? = null

    override val root = stackpane {
        useMaxWidth = true
        useMaxHeight = true
        style {
            backgroundColor += Color.WHITESMOKE
        }

//        fun startDrag(evt : MouseEvent) {
//
//            rectangles
//                .filter {
//                    val mousePt = it.sceneToLocal( evt.sceneX, evt.sceneY )
//                    it.contains(mousePt)
//                }
//                .firstOrNull()
//                .apply {
//                    if( this != null ) {
//
//                        selectedRectangle = this
//
//                        val mp = this.parent.sceneToLocal( evt.sceneX, evt.sceneY )
//                        val vizBounds = this.boundsInParent
//
//                        selectedOffset = Point2D(
//                            mp.x - vizBounds.minX - (vizBounds.width - this.boundsInLocal.width)/2,
//                            mp.y - vizBounds.minY - (vizBounds.height - this.boundsInLocal.height)/2
//                        )
//                    }
//                }
//        }
    }
}