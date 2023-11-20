package deprecated.wholething

import deprecated.Animator
import java.awt.Color
import java.awt.image.BufferedImage

class BPM(private val bpm: Double) {
    operator fun div(value: Double): BPM {
        return BPM(bpm / value)
    }

    operator fun times(value: Double): BPM {
        return BPM(bpm * value)
    }

    val bpmValue: Double
        get() = bpm

    fun toCycleTime(): Double {
        return 60.0 / bpm // Convert BPM to cycle time in seconds
    }

}

class Hz(private val hz: Double) {
    operator fun div(value: Double): Hz {
        return Hz(hz / value)
    }

    operator fun times(value: Double): Hz {
        return Hz(hz * value)
    }

    val hzValue: Double
        get() = hz

    fun toCycleTime(): Double {
        return 1.0 / hz // Convert Hz to cycle time in seconds
    }
}

interface Layer {
    fun evaluate(x: Int, y: Int, time: Double): Double
}

open class BaseLayer(
    private val cycleTime: Double = 1.0,
    private val framerate: Double = 60.0,
    private val squareX: Int = 0,
    private val squareY: Int = 0,
    private val squareWidth: Int = 100,
    private val squareHeight: Int = 100
) : Layer {
    override fun evaluate(x: Int, y: Int, time: Double): Double {
        val cyclePosition = (time / cycleTime) % 1.0
        val currentFrame = (framerate * time).toInt()
        val isInSquare = (x in squareX until squareX + squareWidth) &&
                (y in squareY until squareY + squareHeight)
        return if (isInSquare) cyclePosition else 0.0
    }
}

class CosineWaveLayer(
    cycleTime: Double,
    framerate: Double = 75.0,
    squareX: Int = 0,
    squareY: Int = 0,
    squareWidth: Int = 100,
    squareHeight: Int = 100
) : BaseLayer(cycleTime, framerate, squareX, squareY, squareWidth, squareHeight) {
    override fun evaluate(x: Int, y: Int, time: Double): Double {
        val baseValue = super.evaluate(x, y, time) // Get the base value

        // Apply your specific calculation, for example: ... * 2 - 1
        val result = baseValue * 2 - 1

        return result
    }
}

fun createImage(width: Int, height: Int, layers: List<Layer>, time: Double): BufferedImage {
    // Create an image and draw on it using the provided layers and time
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()

    for (x in 0 until width) {
        for (y in 0 until height) {
            var pixelValue = 0.0
            for (layer in layers) {
                pixelValue += layer.evaluate(x, y, time)
            }
            pixelValue /= layers.size.toDouble()

            // Clamp the pixelValue to the valid range [0, 255]
            val colorValue = (pixelValue * 255).coerceIn(0.0, 255.0).toInt()
            val color = Color(colorValue, colorValue, colorValue)
            graphics.color = color
            graphics.fillRect(x, y, 1, 1)
        }
    }

    graphics.dispose()
    return image
}

class BinaryLayer : Layer {
    override fun evaluate(x: Int, y: Int, time: Double): Double {
        // Calculate the current cycle position based on time
        val cyclePosition = (time * 2) % 1.0

        // If the cycle position is less than 0.5, return 0.0 (black), otherwise return 1.0 (white)
        return if (cyclePosition < 0.5) 0.0 else 1.0
    }
}


fun main() {
    val bpm = BPM(120.0) / 2.0 // Replace with your desired BPM
    val cycleTime = bpm.toCycleTime()

    val width = 800 // replace with your desired width
    val height = 600 // replace with your desired height

    // Set up the display window
    //DisplayWindow.setup(width, height)

    val binaryLayer = BinaryLayer() // Create a BinaryLayer

    // Create an animator with the desired frame rate and layers
    val animator = Animator(75, listOf(binaryLayer), width, height)

    // Use the setFrameListener extension function to set the frame listener
    animator.setFrameListener { elapsedTime ->
        val time = elapsedTime // Update time as needed

        val resultBinary = binaryLayer.evaluate(0, 0, time) // Use (0, 0) as the coordinates

        println("Result at time $time (Binary io.kstud.khack.kgl.Layer): $resultBinary")

        // Repaint the display window with the current image
        val image = createImage(width, height, listOf(binaryLayer), time)
        //DisplayWindow.repaint(image)
    }

    animator.start()

    // Run indefinitely to keep the program running
    while (true) {
        // You can add any additional logic here if needed
        Thread.sleep(1000) // Sleep to avoid high CPU usage
    }
}



