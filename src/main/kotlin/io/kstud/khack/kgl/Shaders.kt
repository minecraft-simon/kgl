package io.kstud.khack.kgl

const val VERTEX_SHADER_SOURCE = """
    #version 330 core
    layout (location = 0) in vec3 aPos;
    uniform mat4 uMatrix;
    void main() {
        gl_Position = uMatrix * vec4(aPos, 1.0);
    }
"""

const val FRAGMENT_SHADER_SOURCE = """
    #version 330 core
    out vec4 FragColor;
    void main() {
    // Calculate the gradient color based on the fragment's position
    float gradient = (gl_FragCoord.x / 800.0); // Assuming a width of 800 pixels

    FragColor = vec4(gradient, gradient, gradient, 1.0);
}

"""
