package io.kstud.khack.kgl

data class Position(
    val x: Float,
    val y: Float,
    val z: Float
)

data class Rectangle(
    val position: Position,
    val width: Float,
    val height: Float
) {
    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) : this(Position(x, y, 0f), width, height)

    fun getVertices(): FloatArray {
        return floatArrayOf(
            x, y, z, // Top left
            x + width, y, z, // Top right
            x + width, y + height, z, // Bottom right
            x, y + height, z // Bottom left
        )
    }

    val x: Float
        get() = position.x

    val y: Float
        get() = position.y

    val z: Float
        get() = position.z

}