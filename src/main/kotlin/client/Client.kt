package client

import tornadofx.*
import clientview.Interface
import javafx.application.Application
import javafx.stage.Stage

fun main(args: Array<String>) {
    val address = "localhost" //hard coded, but could just as easily be taken as an argument from commandline or UI
    val port = 9999

    Application.launch(ClientApp::class.java, *args)
//    client.run()
}

class ClientApp: App() {
    override val primaryView = Interface::class
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1024.0
        stage.height = 768.0
    }
}