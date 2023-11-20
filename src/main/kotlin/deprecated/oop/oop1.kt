package deprecated.oop

import ShaderProgram
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*

// Rectangle.kt
class Rectangle(private val x: Float, private val y: Float, private val width: Float, private val height: Float) {
    fun getVertices(): FloatArray {
        return floatArrayOf(
            x, y + height, 0.0f, // Top Left
            x + width, y + height, 0.0f, // Top Right
            x + width, y, 0.0f, // Bottom Right
            x, y, 0.0f // Bottom Left
        )
    }

    fun getIndices(): IntArray {
        return intArrayOf(
            0, 1, 2, 2, 3, 0 // Two triangles
        )
    }
}

// Shader.kt
class Shader(private val type: Int, source: String) {
    var id: Int = glCreateShader(type)

    init {
        glShaderSource(id, source)
        glCompileShader(id)
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to compile shader: ${glGetShaderInfoLog(id)}")
        }
    }

    fun use() {
        glUseProgram(id)
    }

    fun delete() {
        glDeleteShader(id)
    }
}


class Renderer {
    private lateinit var shaderProgram: ShaderProgram

    fun setup() {
        // Initialize GLFW, create window, initialize GL, etc.
        shaderProgram = ShaderProgram()
    }

    fun render(rectangle: Rectangle) {
        val vertices = rectangle.getVertices()
        val indices = rectangle.getIndices()

        // Setup VAO, VBO, EBO, etc., using vertices and indices
        shaderProgram.use()
        // Draw the rectangle
    }

    fun cleanup() {
        // Cleanup resources
        shaderProgram.delete()
        // Delete VAO, VBO, EBO, etc.
    }
}


class Window(title: String, width: Int, height: Int) {
    private var windowHandle: Long = 0
    var width: Int = width
        private set
    var height: Int = height
        private set

    init {
        if (!glfwInit()) {
            throw IllegalStateException("Failed to initialize GLFW")
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        windowHandle = glfwCreateWindow(width, height, title, 0, 0)
        if (windowHandle == 0L) {
            throw RuntimeException("Failed to create GLFW window")
        }

        glfwMakeContextCurrent(windowHandle)
        glfwSwapInterval(1) // Enable vsync

        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(windowHandle) { _, newWidth, newHeight ->
            this.width = newWidth
            this.height = newHeight
            glViewport(0, 0, newWidth, newHeight)
            println("Framebuffer resized to $newWidth x $newHeight")
        }
    }

    fun shouldClose(): Boolean {
        return glfwWindowShouldClose(windowHandle)
    }

    fun update() {
        glfwSwapBuffers(windowHandle)
        glfwPollEvents()
    }

    fun cleanup() {
        glfwDestroyWindow(windowHandle)
        glfwTerminate()
    }
}


fun main() {
    val window = Window("Simple OpenGL Example", 800, 600)
    val renderer = Renderer()
    val rectangle = Rectangle(0f, 0f, 100f, 100f)

    renderer.setup()

    while (!window.shouldClose()) {
        renderer.render(rectangle)
        window.update()
    }

    renderer.cleanup()
    window.cleanup()
}
