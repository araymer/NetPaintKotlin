package controllers

import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import model.*
import java.io.Serializable
import java.util.*
import kotlin.concurrent.thread
import server.ClientHandler

class Canvas: Serializable {
    val clients: LinkedList<ClientHandler> = LinkedList()
    val id = ObjectIdGenerators.UUIDGenerator().generateId(this).toString()
    private val layers: LinkedList<Layer> = LinkedList()
    private val messageQueue: LinkedList<Message> = LinkedList()
    private val mapper = jacksonObjectMapper()
    private var isRunning = true
    private val undoLayers: LinkedList<Int> = LinkedList()

    init {
        layers.push(Layer())
        thread {
            while(isRunning) {
                processMessage()
            }
        }
    }

    fun addToMessageQueue(message: Message) {
        messageQueue.push(message)
    }

    fun killCanvas() {
        isRunning = false
    }

    private fun processMessage() {
        val message = messageQueue.pop()

        var shape: Shape? = null

        if(message != null) {

            when (message.action) {
                "DRAW_RECTANGLE" -> {
                    shape = message.info?.let { mapper.readValue(it) as Rectangle? }
                }
                "DRAW_ELLIPSE" -> {
                    shape = message.info?.let { mapper.readValue(it) as Ellipse? }
                }
                "DRAW_LINE" -> {
                    shape = message.info?.let { mapper.readValue(it) as Line? }
                }
                "DRAW_SQUIGGLE" -> {
                    shape = message.info?.let { mapper.readValue(it) as Squiggle? }
                }
                "DRAW_TRIANGLE" -> {
                    shape = message.info?.let { mapper.readValue(it) as Triangle? }
                }
                "UNDO" -> {
                    message.layerIndex?.let {
                        layers[it].undo()
                        undoLayers.push(it)
                        if (undoLayers.size > 20) {
                            undoLayers.removeLast()
                        }
                    }
                }
                "REDO" -> {
                    layers[undoLayers.pop()].redo()
                }
                "ADD_LAYER" -> {
                    layers.push(Layer())
                }
                "REMOVE_LAYER" -> {
                    message.layerIndex?.let {
                        layers.removeAt(it)
                    }
                }
                else -> {
                }
            }

        }

        message.layerIndex?.let {layers[it].addShape(shape) }
        notifyClients()

    }

    private fun notifyClients() {
    //TODO: Sync all clients to the canvas so they can all display the same content

    }
}
