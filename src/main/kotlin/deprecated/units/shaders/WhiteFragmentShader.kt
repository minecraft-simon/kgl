package deprecated.units.shaders

import deprecated.units.BaseShader

/**
 * A simple fragment shader that renders all fragments in solid white color.
 * This shader is useful for basic rendering when all elements need to be displayed in white.
 *
 * @param filePath The path to the shader source file (default: "shaders/WhiteFragmentShader.glsl").
 * @param shaderType The shader type (default: [org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER]).
 */
class WhiteFragmentShader(
    override val filePath: String = "shaders/WhiteFragmentShader.glsl",
    override val shaderType: Int = org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
) : BaseShader() {

    override fun loadSource(): String {
        return """
            #version 330 core
            in vec4 vertexColor;
            out vec4 FragColor;
            
            void main() {
                FragColor = vec4(1.0, 1.0, 1.0, 1.0); // Render solid white color
            }
        """.trimIndent()
    }
}
