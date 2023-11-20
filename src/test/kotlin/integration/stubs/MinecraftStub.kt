package io.kstud.khack.kgl.integration.stubs

import deprecated.KGLRenderer
import deprecated.Layer
import net.khack.debugging.Trace
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL33
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.system.exitProcess

class MinecraftStub : Stub {
    companion object {
        private const val DEFAULT_FPS = 60
        private var windowWidth = 854
        private var windowHeight = 480

        @JvmStatic
        fun main(args: Array<String>) {
            Trace.log("MinecraftStub.main()")
            val targetFPS = args.getOrNull(0)?.toIntOrNull() ?: DEFAULT_FPS
            val targetFrameTime = 1.0 / targetFPS

            if (!GLFW.glfwInit()) {
                throw IllegalStateException("Unable to initialize GLFW")
            }

            GLFW.glfwDefaultWindowHints()
            val windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, "KGL Application", 0, 0)
            if (windowHandle == 0L) {
                throw RuntimeException("Failed to create the GLFW window")
            }

            GLFW.glfwMakeContextCurrent(windowHandle)
            GL.createCapabilities()
            GLFW.glfwShowWindow(windowHandle)

            GL33.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

            val layers = CopyOnWriteArrayList<Layer>()
            layers.add(Layer(0.0, 0.0, 100.0, 100.0, 1.0))
            //layers.add(Layer(100.0, 100.0, 600.0, 400.0, 1.0))

            val kglRenderer = KGLRenderer(layers)

            // Setup window size callback
            GLFW.glfwSetWindowSizeCallback(windowHandle) { _, newWidth, newHeight ->
                Trace.log("MinecraftStub.main() -> GLFW.glfwSetWindowSizeCallback()")
                windowWidth = newWidth
                windowHeight = newHeight
                kglRenderer.resize(newWidth.toFloat(), newHeight.toFloat())
            }

            var keepRunning = true  // Added boolean to control the loop
            var lastTime = GLFW.glfwGetTime()
            while (keepRunning) {
                val currentTime = GLFW.glfwGetTime()
                val deltaTime = currentTime - lastTime

                if (deltaTime >= targetFrameTime) {
                    GL33.glClear(GL33.GL_COLOR_BUFFER_BIT)
                    kglRenderer.redraw(windowWidth.toFloat(), windowHeight.toFloat())
                    GLFW.glfwSwapBuffers(windowHandle)

                    lastTime = currentTime
                }

                GLFW.glfwPollEvents()
                if (GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                    keepRunning = false  // Set to false to exit the loop
                }
                if (GLFW.glfwWindowShouldClose(windowHandle)) {
                    keepRunning = false  // Set to false to exit the loop when the window is closed
                }
            }

            GLFW.glfwDestroyWindow(windowHandle)
            GLFW.glfwTerminate()
            GLFW.glfwSetErrorCallback(null)?.free()
            exitProcess(0)
        }
    }

    override fun setup(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

}
