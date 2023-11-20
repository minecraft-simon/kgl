package deprecated.oop3

import ShaderProgram
import net.khack.debugging.Trace
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer

class OpenGlRenderer {
    private lateinit var shaderProgram: ShaderProgram
    private var transform = Matrix4f().ortho(0f, 800f, 600f, 0f, -1f, 1f)
    private var vao: Int = 0
    private var vbo: Int = 0
    private var ebo: Int = 0

    fun setup() {
        // Initialize GLFW, create window, initialize GL, etc.
        shaderProgram = ShaderProgram()
        setupBuffers()
    }

    private fun setupBuffers() {
        Trace.log("KGLRenderer.setupBuffers()")

        // Generate VAO
        vao = GL30.glGenVertexArrays()
        println("Generated VAO with ID: $vao")
        GL30.glBindVertexArray(vao)

        // Generate VBO
        vbo = GL30.glGenBuffers()
        println("Generated VBO with ID: $vbo")

        // Setup vertex attribute pointers
        // Assuming position at location 0, and color at location 1
        val stride = (3 + 4) * 4 // 3 for position, 4 for color, times size of float
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo)
        GL30.glEnableVertexAttribArray(0) // Enable position attribute
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, stride, 0)
        GL30.glEnableVertexAttribArray(1) // Enable color attribute
        GL30.glVertexAttribPointer(1, 4, GL30.GL_FLOAT, false, stride, 12L)
        println("Vertex attribute pointers set up with stride: $stride")


        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)
        GL30.glBindVertexArray(0)

        println("Buffers setup complete")
    }

    fun render() {
        // Define the vertices of the rectangle (two triangles forming a rectangle)
        val vertices = floatArrayOf(
            100f, 100f, 0f, 1f, 0f, 0f, 1f, // Vertex 1: x, y, z, r, g, b, a
            200f, 100f, 0f, 0f, 1f, 0f, 1f, // Vertex 2: x, y, z, r, g, b, a
            200f, 200f, 0f, 0f, 0f, 1f, 1f, // Vertex 3: x, y, z, r, g, b, a
            100f, 200f, 0f, 1f, 1f, 0f, 1f  // Vertex 4: x, y, z, r, g, b, a
        )

        // Create a FloatBuffer for the vertices
        val vertexBuffer: FloatBuffer = BufferUtils.createFloatBuffer(vertices.size)
        vertexBuffer.put(vertices)
        vertexBuffer.flip()

        // Bind the VAO and VBO
        GL30.glBindVertexArray(vao)
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo)

        // Upload the vertex data to the VBO
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL30.GL_STATIC_DRAW)

        // Use the shader program for rendering
        shaderProgram.use()

        // Set the transformation matrix in the shader program
        //shaderProgram.setUniformMatrix4fv("transform", false, transform)

        // Draw the rectangle (6 vertices for 2 triangles)
        GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 6)

        // Unbind the VAO and VBO
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0)
        GL30.glBindVertexArray(0)
    }


    fun resize(width: Float, height: Float) {
        transform = Matrix4f().ortho(0f, width, height, 0f, -1f, 1f)
        glViewport(0, 0, width.toInt(), height.toInt())
    }

    fun cleanup() {
        // Cleanup resources
        shaderProgram.delete()
        // Delete VAO, VBO, EBO, etc.
    }
}