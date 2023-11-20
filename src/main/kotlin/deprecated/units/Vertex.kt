package deprecated.units

import deprecated.units.color.KGLColor
import java.awt.Color

data class Vertex(
    val position: Position,
    val color: Color = Color.WHITE,
)