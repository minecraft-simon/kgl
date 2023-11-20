package deprecated.oop2

import org.lwjgl.opengl.GL20

abstract class Shader(
    type: Int,
    source: String
) {
    var id: Int = GL20.glCreateShader(type)

    init {
        GL20.glShaderSource(id, source)
        GL20.glCompileShader(id)
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            throw RuntimeException("Failed to compile shader: ${GL20.glGetShaderInfoLog(id)}")
        }
    }

    fun use() {
        GL20.glUseProgram(id)
    }

    fun delete() {
        GL20.glDeleteShader(id)
    }
}

open class VertexShader(source: String) : Shader(GL20.GL_VERTEX_SHADER, source)
open class FragmentShader(source: String) : Shader(GL20.GL_FRAGMENT_SHADER, source)

class BasicVertexShader : VertexShader(
    """
        #version 330 core
        layout (location = 0) in vec3 aPos;
        uniform mat4 uMatrix;
        void main() {
            gl_Position = uMatrix * vec4(aPos, 1.0);
        }
    """.trimIndent()
)

class BasicFragmentShader : FragmentShader(
    """
        #version 330 core
        out vec4 FragColor;
        void main() {
            FragColor = vec4(1.0, 0.5, 0.2, 1.0); // Orange color
        }
    """.trimIndent()
)