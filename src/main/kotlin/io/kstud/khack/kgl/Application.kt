package io.kstud.khack.kgl

import OpenGlRenderer
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL

fun main() {
    val application = Application()
    application.run()
}

class Application {
    private lateinit var renderer: OpenGlRenderer

    fun run() {
        init()
        loop()

        // Terminate GLFW
        glfwTerminate()
    }

    private fun init() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw IllegalStateException("Failed to initialize GLFW")
        }

        // Configure GLFW
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        // Create a window
        val window = glfwCreateWindow(800, 600, "Simple OpenGL Example", 0, 0)
        if (window == 0L) {
            throw RuntimeException("Failed to create GLFW window")
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        glfwSwapInterval(1) // Enable vsync

        // Initialize GL
        GL.createCapabilities()

        // Initialize Renderer
        renderer = OpenGlRenderer(window)

        // Set the framebuffer size callback
        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            renderer.resize(width.toFloat(), height.toFloat())
        }

    }

    private fun loop() {
        while (!glfwWindowShouldClose(renderer.window)) {
            val rectangle1 = Rectangle(100f, 0f, 100f, 100f)
            val rectangle2 = Rectangle(0f, 100f, 100f, 100f)
            renderer.render(
                listOf(
                    rectangle1,
                    rectangle2
                )
            )

            // Swap the front and back buffers
            glfwSwapBuffers(renderer.window)

            // Poll for and process events
            glfwPollEvents()
        }

        renderer.cleanup()
    }
}
