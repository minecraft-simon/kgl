package deprecated.oop3

import io.kstud.khack.kgl.Rectangle
import io.kstud.khack.kgl.Renderer

class KGLRenderer(
    private val renderer: Renderer
) : Renderer {

    override fun render(rectangles: List<Rectangle>) {
        renderer.render(rectangles)
    }

    override fun resize(width: Float, height: Float) {
        renderer.resize(width, height)
    }

    override fun cleanup() {
        renderer.cleanup()
    }

}