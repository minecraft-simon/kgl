package io.kstud.khack.kgl

import org.lwjgl.opengl.GL20

class Shader(private val type: Int, source: String) {
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