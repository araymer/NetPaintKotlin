package clientview

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

enum class TOOL {
    SELECT, RECTANGLE, CIRCLE, TRIANGLE, LINE, SQUIGGLE
}

var selectedColor = Color.WHITE

class Toolbar: View("Toolbar") {

    val icons = listOf("/cursor.png", "/circle.png", "/line.png", "/rectangle.png", "/curve.png", "/triangle.png")
    val tools = listOf(TOOL.SELECT, TOOL.CIRCLE, TOOL.LINE, TOOL.RECTANGLE, TOOL.SQUIGGLE, TOOL.TRIANGLE)

    val selectTool = {tool: TOOL -> shapeCanvas?.selectedTool = tool}

    override val root = vbox {
        useMaxHeight = true
        style {
            backgroundColor += Color.LIGHTGRAY
            minWidth = 100.px
        }
        vbox {
            useMaxWidth = true
            style {
                padding = box(20.px, 0.px, 15.px, 0.px)
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
        for(i in icons.indices step 2) {
            hbox {
                useMaxWidth = true
                button("", ImageView(Image(icons[i],15.0, 15.0, true, true))) {
                    style {
                        minWidth = 50.px
                    }
                    action {
                        selectTool(tools[i])
                    }
                }
                button("", ImageView(Image(icons[i+1], 15.0, 15.0, true, true))) {
                    style {
                        minWidth = 50.px
                    }
                    action {
                        selectTool(tools[i+1])
                    }
                }
            }
        }
        hbox {
            style {
                maxWidth = Dimension(50.0, Dimension.LinearUnits.px)
            }
            colorpicker {
                setOnAction {
                    selectedColor = this@colorpicker.value
                }
            }
        }
    }
}