package io.kstud.khack.kgl

import io.kstud.khack.kgl.Rectangle

interface Renderer {
    fun render(rectangles: List<Rectangle>)
    fun resize(width: Float, height: Float)
    fun cleanup()
}