package deprecated.units

import org.lwjgl.opengl.GL20.*

abstract class BaseShader : Shader {
    abstract val filePath: String
    abstract val shaderType: Int

    override fun loadSource(): String {
        // Assumes the resources folder is set up correctly in your project
        return javaClass.getResource(filePath)?.readText() ?: throw IllegalStateException("Shader file not found")
    }

    override fun compile(): Int {
        val source = loadSource()

        val shaderId = glCreateShader(shaderType)
        glShaderSource(shaderId, source)
        glCompileShader(shaderId)

        // Error handling...
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            val infoLog = glGetShaderInfoLog(shaderId)
            println("ERROR::SHADER::COMPILATION_FAILED\n$infoLog")
            throw Exception("Shader compilation error: $infoLog")
        }

        return shaderId
    }
}
