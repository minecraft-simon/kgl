import io.kstud.khack.kgl.FRAGMENT_SHADER_SOURCE
import io.kstud.khack.kgl.Shader
import io.kstud.khack.kgl.VERTEX_SHADER_SOURCE
import org.lwjgl.opengl.GL20.*

class ShaderProgram {
    var programId: Int = glCreateProgram()

    init {
        val vertexShader = Shader(GL_VERTEX_SHADER, VERTEX_SHADER_SOURCE)
        val fragmentShader = Shader(GL_FRAGMENT_SHADER, FRAGMENT_SHADER_SOURCE)

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

    fun getUniformLocation(name: String): Int {
        return glGetUniformLocation(programId, name)
    }
}
