package deprecated

import deprecated.units.Position
import deprecated.units.Rect
import deprecated.units.Vertex
import java.awt.Color

data class Layer(
    var x: Double,
    var y: Double,
    var width: Double,
    var height: Double,
    var opacity: Double
) {
    var backgroundColor: Color? = null

    fun getRectangle(screenWidth: Float, screenHeight: Float): Rect {
        val left = ((2.0 * x / screenWidth) - 1).toFloat()
        val right = ((2.0 * (x + width) / screenWidth) - 1).toFloat()
        val top = (1 - (2.0 * y / screenHeight)).toFloat()
        val bottom = (1 - (2.0 * (y + height) / screenHeight)).toFloat()

        val color = backgroundColor ?: Color.WHITE

        return Rect(
            Vertex(Position(left, top ), color), // Top left corner
            Vertex(Position(left, bottom), color), // Bottom left corner
            Vertex(Position(right, top), color), // Top right corner
            Vertex(Position(right, bottom), color) // Bottom right corner
        )
    }

}