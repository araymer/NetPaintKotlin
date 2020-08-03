package clientview

import tornadofx.*

class Interface : View("NetPaint") {
    override val root = vbox{
        button("Cleeek") {
            action {
                println("YOU CLICKED IT!")
            }
        }
        label("WEEEEE")
    }

}