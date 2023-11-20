package deprecated.units.shaders

import deprecated.units.BaseShader

class FragmentShaderExample(
    override val filePath: String = "shaders/FragmentShaderExample.glsl",
    override val shaderType: Int = org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
) : BaseShader() {

    override fun loadSource(): String {
        return """
            #version 330 core
            in vec4 vertexColor;
            out vec4 FragColor;
            
            void main() {
                FragColor = vec4(1.0, 1.0, 1.0, 1.0); // Render white color
            }
        """.trimIndent()
    }

}
