package clientview

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Interface : View("NetPaint Client") {
    override val root = borderpane() {

        top = menubar {
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

        left = vbox {
            useMaxHeight = true
            style {
                backgroundColor += Color.LIGHTGRAY
                minWidth = 100.px
            }
            vbox {
                useMaxWidth = true
                style {
                    padding = CssBox(35.px, 0.px, 15.px, 0.px)
                }
                label("Tools") {
                    useMaxWidth = true
                    style {
                        fontSize = 16.px
                        fontFamily = "Verdana"
                        fontWeight = FontWeight.BOLD
                        alignment = Pos.CENTER
                    }
                }
            }
            hbox {
                useMaxWidth = true
                button("1") {
                    style {
                        minWidth = 50.px
                    }
                }
                button("2") {
                    style {
                        minWidth = 50.px
                    }
                }
            }
            hbox {
                useMaxWidth = true
                button("3") {
                    style {
                        minWidth = 50.px
                    }
                }
                button("4") {
                    style {
                        minWidth = 50.px
                    }
                }
            }
            hbox {
                useMaxWidth = true
                button("5") {
                    style {
                        minWidth = 50.px
                    }
                }
                button("6") {
                    style {
                        minWidth = 50.px
                    }
                }
            }
        }


        center = stackpane {
            useMaxWidth = true
            useMaxHeight = true
            style {
                backgroundColor += Color.WHITESMOKE
            }
            button("TEXT") {
                style {
                    minWidth = 200.px
                }
            }
            button("OTHER") {
                style {
                    minWidth = 100.px
                    backgroundColor += Color.BLUE
                }
            }
        }
    }
}