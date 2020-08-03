package server

import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import controllers.Canvas
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

val clientMap: HashMap<String, ClientHandler> = HashMap()
val canvasList: LinkedList<Canvas> = LinkedList()

fun main() {
    val server = ServerSocket(9999)
    println("Server is running on port ${server.localPort}")

    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")

        if(client !is Nothing) {
            val handler = ClientHandler(client)
            thread { handler.run() }
        }
    }
}

class ClientHandler(private val client: Socket) {
    private val id = ObjectIdGenerators.UUIDGenerator().generateId(this).toString()
    private val canvas: Canvas = Canvas()
    private val inputStream: Scanner = Scanner(client.getInputStream())
    private val outputStream: OutputStream = client.getOutputStream()
    private val mapper = jacksonObjectMapper()
    private var isRunning = false

    init {
        clientMap[id] = this
        canvasList.add(canvas)
    }

    fun run() {
        isRunning = true

        loop@ while(isRunning) {
            val input = inputStream.nextLine()
            when {
                input.startsWith("EXIT") -> {
                    shutdown()
                    continue@loop
                }
                input.startsWith("NEW_CANVAS") -> {

                }
                input.startsWith("JOIN_CANVAS=") -> {

                }
                else -> {
                    canvas.addToMessageQueue(mapper.readValue(input))

                }
            }
        }
    }

    fun joinCanvas(canvasId: String) {
        val canvasToJoin = canvasList.first { it.id == canvasId }
        if(canvasToJoin !is Nothing) canvasList.forEach {
            it -> run {
                it.clients.removeIf { it.id == id }
                if (it.clients.size == 0) it.killCanvas()
            }
        }
        else {
            throw Exception("No canvas was found with ID")
            return
        }
        canvasToJoin.clients.add(this)
    }

    private fun shutdown() {
        isRunning = false
        clientMap.remove(id)
        canvas.clients.removeIf {
            it -> it.id == id
        }
        if(canvas.clients.size == 0) canvas.killCanvas()
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

}