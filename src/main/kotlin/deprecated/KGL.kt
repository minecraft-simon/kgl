package deprecated

import deprecated.units.shaders.FragmentShaderExample
import deprecated.units.shaders.VertexShaderExample
import net.khack.debugging.Trace
import org.lwjgl.opengl.GL30.*
import java.util.concurrent.CopyOnWriteArrayList

class KGLRenderer(
    val layers: CopyOnWriteArrayList<Layer> = CopyOnWriteArrayList()
) {
    private var shaderProgram: Int = 0
    private var vaoID: Int = 0
    private var vboID: Int = 0

    private var screenWidth: Float = 800f // Default screen width
    private var screenHeight: Float = 600f // Default screen height

    init {
        Trace.log("KGLRenderer.init()")
        setupBuffers()
        setupShaders()
    }

    private fun setupBuffers() {
        Trace.log("KGLRenderer.setupBuffers()")

        // Generate VAO
        vaoID = glGenVertexArrays()
        println("Generated VAO with ID: $vaoID")
        glBindVertexArray(vaoID)

        // Generate VBO
        vboID = glGenBuffers()
        println("Generated VBO with ID: $vboID")

        // Setup vertex attribute pointers
        // Assuming position at location 0, and color at location 1
        val stride = (3 + 4) * 4 // 3 for position, 4 for color, times size of float
        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glEnableVertexAttribArray(0) // Enable position attribute
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0)
        glEnableVertexAttribArray(1) // Enable color attribute
        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 12L)
        println("Vertex attribute pointers set up with stride: $stride")


        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)

        println("Buffers setup complete")
    }

    private fun setupShaders() {
        Trace.log("KGLRenderer.setupShaders()")

        // Compile vertex shader
        val vertexShader = VertexShaderExample().compile()

        // Compile fragment shader
        val fragmentShader = FragmentShaderExample().compile()

        // Create shader program
        shaderProgram = glCreateProgram()

        // Attach shaders
        glAttachShader(shaderProgram, vertexShader)
        glAttachShader(shaderProgram, fragmentShader)

        // Link shader program
        glLinkProgram(shaderProgram)
        println("Shader program linked with ID: $shaderProgram")


        // Check for linking errors
        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            val infoLog = glGetProgramInfoLog(shaderProgram)
            println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n$infoLog")
            throw Exception("Shader program linking error: $infoLog")
        }

        // Delete shaders as they're linked into the program now and no longer necessary
        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)

        println("Shaders setup complete")
    }

    fun redraw(screenWidth: Float, screenHeight: Float) {
        Trace.log("KGLRenderer.redraw($screenWidth, $screenHeight)")

        val orthoMatrix = createOrthoMatrix(screenWidth, screenHeight)

        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        glUseProgram(shaderProgram)

        val orthoMatrixUniform = glGetUniformLocation(shaderProgram, "orthoMatrix")
        glUniformMatrix4fv(orthoMatrixUniform, false, orthoMatrix)

        glBindVertexArray(vaoID)

        for (layer in layers) {
            // Assuming vertexData is prepared properly
            val rect = layer.getRectangle(screenWidth, screenHeight)
            println(rect)
            val vertexData = rect.getVertexData()

            glBindBuffer(GL_ARRAY_BUFFER, vboID)
            glBufferData(GL_ARRAY_BUFFER, vertexData, GL_DYNAMIC_DRAW)

            glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        }

        glBindVertexArray(0)
        glUseProgram(0)
    }


    fun resize(newWidth: Float, newHeight: Float) {
        Trace.log("KGLRenderer.resize($newWidth, $newHeight)")
        screenWidth = newWidth
        screenHeight = newHeight

        // Update anything else that needs to change with window size,
        // such as projection matrix, viewport, etc.
        glViewport(0, 0, screenWidth.toInt(), screenHeight.toInt())

        redraw(screenWidth, screenHeight)
    }


}
