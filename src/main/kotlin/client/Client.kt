package client

import tornadofx.*
import clientview.Interface
import javafx.application.Application

fun main(args: Array<String>) {
    val address = "localhost"
    val port = 9999

    Application.launch(ClientApp::class.java, *args)
//    client.run()
}

class ClientApp(): App() {
    override val primaryView = Interface::class
}