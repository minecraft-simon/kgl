package deprecated.oop2

import org.lwjgl.opengl.GL20.*

class ShaderProgram {
    private var programId: Int = glCreateProgram()

    init {
        val vertexShader = BasicVertexShader()
        val fragmentShader = BasicFragmentShader()

        glAttachShader(programId, vertexShader.id)
        glAttachShader(programId, fragmentShader.id)
        glLinkProgram(programId)

        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to link shader program: ${glGetProgramInfoLog(programId)}")
        }

        // Delete the shaders as they're linked into the program now and no longer necessary
        vertexShader.delete()
        fragmentShader.delete()
    }

    fun use() {
        glUseProgram(programId)
    }

    fun delete() {
        glDeleteProgram(programId)
    }
}
