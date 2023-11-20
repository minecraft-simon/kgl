package deprecated.units.shaders

import deprecated.units.BaseShader

/**
 * A simple 2D vertex shader designed for basic 2D rendering in OpenGL.
 * This shader is useful for rendering 2D elements like buttons, text boxes, and simple shapes onto a 2D rendering context.
 *
 * @param filePath The path to the shader source file (default: "shaders/Simple2DVertexShader.glsl").
 * @param shaderType The shader type (default: [org.lwjgl.opengl.GL20.GL_VERTEX_SHADER]).
 */
class Simple2DVertexShader(
    override val filePath: String = "shaders/Simple2DVertexShader.glsl",
    override val shaderType: Int = org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
) : BaseShader() {

    override fun loadSource(): String {
        return """
            #version 330 core
            layout(location = 0) in vec2 inPosition;
            
            uniform mat4 projectionMatrix;
            
            out vec2 fragTexCoord;
            
            void main() {
                gl_Position = projectionMatrix * vec4(inPosition, 0.0, 1.0);
                fragTexCoord = inPosition; // Pass texture coordinates if needed
            }
        """.trimIndent()
    }
}
