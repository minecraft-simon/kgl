package deprecated.attempt6

import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer

fun main() {
    // Initialize GLFW
    if (!glfwInit()) {
        throw IllegalStateException("Failed to initialize GLFW")
    }

    // Configure GLFW
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

    // Create a window
    val window = glfwCreateWindow(800, 600, "Simple OpenGL Example", 0, 0)
    if (window == 0L) {
        throw RuntimeException("Failed to create GLFW window")
    }

    // Make the OpenGL context current
    glfwMakeContextCurrent(window)
    glfwSwapInterval(1) // Enable vsync

    // Initialize GL
    GL.createCapabilities()

    // Initialize the transformation matrix
    var transform = Matrix4f().ortho(0f, 800f, 600f, 0f, -1f, 1f)

    // Set the framebuffer size callback
    glfwSetFramebufferSizeCallback(window) { _, width, height ->
        transform = Matrix4f().ortho(0f, width.toFloat(), height.toFloat(), 0f, -1f, 1f)
        glViewport(0, 0, width, height)
        // You can also update the projection matrix here if necessary
        println("Framebuffer resized to $width x $height")
    }

    // Create and compile the vertex shader
    val vertexShaderSource = """
        // This shader transforms 3D vertex positions to 2D by setting the z-coordinate to 0.
        #version 330 core
        layout (location = 0) in vec3 aPos;
        uniform mat4 uMatrix;
        void main() {
            gl_Position = uMatrix * vec4(aPos, 1.0);
        }
    """.trimIndent()

    val vertexShader = glCreateShader(GL_VERTEX_SHADER)
    glShaderSource(vertexShader, vertexShaderSource)
    glCompileShader(vertexShader)

    // Check for shader compilation errors
    if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
        val infoLog = glGetShaderInfoLog(vertexShader)
        throw RuntimeException("Vertex shader compilation failed:\n$infoLog")
    }

    // Create and compile the fragment shader
    val fragmentShaderSource = """
        #version 330 core
        out vec4 FragColor;
        void main() {
            FragColor = vec4(1.0, 0.5, 0.2, 1.0); // orange
        }
    """.trimIndent()

    val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(fragmentShader, fragmentShaderSource)
    glCompileShader(fragmentShader)

    // Check for shader compilation errors
    if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
        val infoLog = glGetShaderInfoLog(fragmentShader)
        throw RuntimeException("Fragment shader compilation failed:\n$infoLog")
    }

    // Create and link the shader program
    val shaderProgram = glCreateProgram()
    glAttachShader(shaderProgram, vertexShader)
    glAttachShader(shaderProgram, fragmentShader)
    glLinkProgram(shaderProgram)

    // Check for shader program linking errors
    if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
        val infoLog = glGetProgramInfoLog(shaderProgram)
        throw RuntimeException("Shader program linking failed:\n$infoLog")
    }

    // Delete the individual shaders since they are now linked into the program
    glDeleteShader(vertexShader)
    glDeleteShader(fragmentShader)

    // Define the vertices of the rectangle (0,0,100,100)
    val vertices = floatArrayOf(
        0f, 100f, 0.0f, // Top left
        100f, 100f, 0.0f, // Top right
        100f, 0f, 0.0f, // Bottom right
        0f, 0f, 0.0f // Bottom left
    )

    // Define the indices for the rectangle
    val indices = intArrayOf(
        0, 1, 2,
        2, 3, 0
    )

    // Create and bind a Vertex Array Object (VAO)
    val vao = glGenVertexArrays()
    glBindVertexArray(vao)

    // Create a Vertex Buffer Object (VBO) for the vertex data
    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    val vertexBuffer: FloatBuffer = BufferUtils.createFloatBuffer(vertices.size)
    vertexBuffer.put(vertices).flip()
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW)

    // Create an Element Buffer Object (EBO) for the indices
    val ebo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    val indexBuffer = BufferUtils.createIntBuffer(indices.size)
    indexBuffer.put(indices).flip()
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW)

    // Specify the layout of the vertex data
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
    glEnableVertexAttribArray(0)

    // Unbind the VAO
    glBindVertexArray(0)

    // Main rendering loop
    while (!glfwWindowShouldClose(window)) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)

        // Use the shader program
        glUseProgram(shaderProgram)

        // Pass the transformation matrix to the shader
        val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)
        transform.get(matrixBuffer)
        val matrixLocation = glGetUniformLocation(shaderProgram, "uMatrix")
        glUniformMatrix4fv(matrixLocation, false, matrixBuffer)

        // Bind the VAO
        glBindVertexArray(vao)

        // Draw the rectangle using indices
        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)

        // Unbind the VAO and shader program
        glBindVertexArray(0)
        glUseProgram(0)

        // Swap the front and back buffers
        glfwSwapBuffers(window)

        // Poll for and process events
        glfwPollEvents()
    }

    // Clean up
    glDeleteVertexArrays(vao)
    glDeleteBuffers(vbo)
    glDeleteBuffers(ebo)
    glDeleteProgram(shaderProgram)

    // Terminate GLFW
    glfwTerminate()
}
