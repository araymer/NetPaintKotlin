package model


data class Message(
    val targetCanvas: String,
    val layerIndex: Int?,
    val sourceClient: String,
    val action: String,
    val info: String?,
    val timestamp: String
)