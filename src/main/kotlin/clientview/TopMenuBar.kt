package clientview

import tornadofx.View
import tornadofx.item
import tornadofx.menu
import tornadofx.menubar

class TopMenuBar: View() {
    override val root = menubar {
        menu("File") {
            item("New Canvas")
            item("Exit")
        }
        menu("Edit") {
            item("Undo")
            item("Redo")
        }
        menu("Connect") {
            item("Connect to a Canvas")
            item("Visit WGU Beyond Portal")
        }
    }
}