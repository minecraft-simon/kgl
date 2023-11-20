package deprecated.oop2

import deprecated.oop.Rectangle
import net.khack.debugging.Trace
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL33
import kotlin.system.exitProcess

class MinecraftStub {
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

            //val layers = CopyOnWriteArrayList<Layer>()
            //layers.add(Layer(0.0, 0.0, 100.0, 100.0, 1.0))
            ////layers.add(Layer(100.0, 100.0, 600.0, 400.0, 1.0))

            //val kglRenderer = KGLRenderer(layers)
            val renderer = Renderer()
            renderer.setup()

            /*// Setup window size callback
            GLFW.glfwSetWindowSizeCallback(windowHandle) { _, newWidth, newHeight ->
                Trace.log("MinecraftStub.main() -> GLFW.glfwSetWindowSizeCallback()")
                windowWidth = newWidth
                windowHeight = newHeight
                kglRenderer.resize(newWidth.toFloat(), newHeight.toFloat())
            }*/

            // Set the framebuffer size callback
            GLFW.glfwSetFramebufferSizeCallback(windowHandle) { _, width, height ->
                //transform = Matrix4f().ortho(0f, width.toFloat(), height.toFloat(), 0f, -1f, 1f)
                //GL30.glViewport(0, 0, width, height)
                //// You can also update the projection matrix here if necessary
                //println("Framebuffer resized to $width x $height")
                renderer.resize(width.toFloat(), height.toFloat())
            }

            var keepRunning = true  // Added boolean to control the loop
            var lastTime = GLFW.glfwGetTime()
            // Main rendering loop
            while (!glfwWindowShouldClose(windowHandle)) {
                glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
                glClear(GL_COLOR_BUFFER_BIT)

                // Render the rectangle
                renderer.render(
                    Rectangle(
                        0f,
                        0f,
                        100f,
                        100f
                    )
                )

                // Swap the front and back buffers
                glfwSwapBuffers(windowHandle)

                // Poll for and process events
                glfwPollEvents()
            }

            GLFW.glfwDestroyWindow(windowHandle)
            GLFW.glfwTerminate()
            GLFW.glfwSetErrorCallback(null)?.free()
            exitProcess(0)
        }
    }

}
