package deprecated

import deprecated.wholething.Layer
import deprecated.wholething.createImage
import javax.swing.Timer

class Animator(
    frameRate: Int, // Desired frame rate
    private val layers: List<Layer>,
    private val width: Int,
    private val height: Int
) {
    private val frameTime = 1000 / frameRate
    private var startTime = System.currentTimeMillis()
    private var timer: Timer? = null
    private var frameListener: ((elapsedTime: Double) -> Unit)? = null

    fun start() {
        timer = Timer(frameTime) {
            // Calculate the elapsed time based on the actual clock
            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0
            val cycleTime = (elapsedTime % 0.5) * 4 // Cycle time adjusted for 2 Hz

            // Update and repaint the image
            val image = createImage(width, height, layers, cycleTime)
            //DisplayWindow.repaint(image)
        }
        timer?.start()
    }

    fun stop() {
        timer?.stop()
    }

    fun setFrameListener(listener: (elapsedTime: Double) -> Unit) {
        frameListener = listener
    }
}
