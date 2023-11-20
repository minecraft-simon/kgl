package deprecated.units.shaders

import deprecated.units.BaseShader

class VertexShaderExample(
    override val filePath: String = "shaders/VertexShaderExample.glsl",
    override val shaderType: Int = org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
) : BaseShader() {

    override fun loadSource(): String {
        return """
            #version 330 core
            layout(location = 0) in vec3 position;
            layout(location = 1) in vec4 color;
            
            uniform mat4 orthoMatrix;
            
            out vec4 vertexColor;
            
            void main() {
                gl_Position = orthoMatrix * vec4(position, 1.0);
                vertexColor = color;
            }

        """.trimIndent()
    }

}