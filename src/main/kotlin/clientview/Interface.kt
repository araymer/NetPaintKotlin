package clientview

import tornadofx.*

class Interface : View("NetPaint Client") {
    override val root = borderpane() {

        top(TopMenuBar::class)
        center(ShapeCanvas::class)
        left(Toolbar::class)

    }
}
