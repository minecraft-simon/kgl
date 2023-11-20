import io.kstud.khack.kgl.Rectangle
import io.kstud.khack.kgl.Renderer
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30.*

class OpenGlRenderer(
    val window: Long,
    width: Float = 800f,
    height: Float = 600f,
    var scalingFactor: Float = 1.0f
) : Renderer {
    private var transform: Matrix4f
    private var shaderProgram: ShaderProgram
    private var vao: Int = 0
    private var vbo: Int = 0
    private var ebo: Int = 0

    init {
        // Initialize the transformation matrix
        transform = Matrix4f().ortho(0f, width, height, 0f, -1f, 1f)

        // Initialize shaders and buffers
        shaderProgram = ShaderProgram()

        // Define the vertices and indices of the rectangle
        val vertices = floatArrayOf(
            0f, 100f, 0.0f, // Top left
            100f, 100f, 0.0f, // Top right
            100f, 0f, 0.0f, // Bottom right
            0f, 0f, 0.0f // Bottom left
        )
        val indices = intArrayOf(
            0, 1, 2,
            2, 3, 0
        )

        // Initialize VAO, VBO, EBO
        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        ebo = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
        glEnableVertexAttribArray(0)

        glBindVertexArray(0)
    }

    override fun render(rectangles: List<Rectangle>) {
        glClearColor(255.0f, 0.0f, 0.0f, 0.5f)
        glClear(GL_COLOR_BUFFER_BIT)

        // Use the shader program
        shaderProgram.use()

        // Bind the VAO
        glBindVertexArray(vao)

        for (rectangle in rectangles) {
            // Update vertices based on the current rectangle
            val vertices = rectangle.getVertices()

            glBindBuffer(GL_ARRAY_BUFFER, vbo)
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW)

            // Create a new transformation matrix with scaling factor
            val scalingFactor = 1.0f
            val matrixBuffer = BufferUtils.createFloatBuffer(16)
            val scalingMatrix = Matrix4f().scale(scalingFactor, scalingFactor, 1.0f)
            val transformedMatrix = Matrix4f(transform).mul(scalingMatrix)
            transformedMatrix.get(matrixBuffer)

            // Pass the transformation matrix to the shader
            glUniformMatrix4fv(glGetUniformLocation(shaderProgram.programId, "uMatrix"), false, matrixBuffer)

            // Draw the current rectangle
            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
        }

        // Unbind the VAO and shader program
        glBindVertexArray(0)
        glUseProgram(0)
    }


    override fun resize(width: Float, height: Float) {
        // if any value is 0, return to avoid division by 0
        if (width == 0f || height == 0f) {
            return
        }
        // if any value is < 1000, adjust the scaling factor to fit everything in the window
        if (width < 1000f || height < 1000f) {
            val widthFactor = width / 1000f
            val heightFactor = height / 1000f
            val minFactor = if (widthFactor < heightFactor) widthFactor else heightFactor
            scalingFactor = minFactor
            transform = Matrix4f().ortho(0f, width, height, 0f, -1f, 1f)
                .scale(minFactor)
        } else {
            scalingFactor = 1.0f
            transform = Matrix4f().ortho(0f, width, height, 0f, -1f, 1f)
        }

        glViewport(0, 0, width.toInt(), height.toInt())
    }

    override fun cleanup() {
        // Clean up
        glDeleteVertexArrays(vao)
        glDeleteBuffers(vbo)
        glDeleteBuffers(ebo)
        shaderProgram.delete()
    }
}
