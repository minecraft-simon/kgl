package deprecated.oop3

import deprecated.Layer
import net.khack.debugging.Trace
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL33
import java.util.concurrent.CopyOnWriteArrayList

class MinecraftStub(
    private var windowWidth: Int = 800,
    private var windowHeight: Int = 600,
    private var targetFPS: Int = 60
) {
    private val targetFrameTime = 1.0 / targetFPS
    private var windowHandle = 0L
    //private val kglRenderer: KGLRenderer = KGLRenderer()

    fun setup() {
        Trace.log("MinecraftStub.main()")

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, "KGL Application", 0, 0)
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

        //kglRenderer = KGLRenderer(layers)

        // Setup window size callback
        GLFW.glfwSetWindowSizeCallback(windowHandle) { _, newWidth, newHeight ->
            Trace.log("MinecraftStub.main() -> GLFW.glfwSetWindowSizeCallback()")
            windowWidth = newWidth
            windowHeight = newHeight
            //kglRenderer.resize(newWidth.toFloat(), newHeight.toFloat())
        }
    }

    fun start() {
        var lastTime = GLFW.glfwGetTime()
        var keepRunning = true
        while (keepRunning) {
            val currentTime = GLFW.glfwGetTime()
            val deltaTime = currentTime - lastTime

            if (deltaTime >= targetFrameTime) {
                GL33.glClear(GL33.GL_COLOR_BUFFER_BIT)

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
    }

}

fun main() {
    val minecraftStub = MinecraftStub()
    minecraftStub.setup()
    minecraftStub.start()
}