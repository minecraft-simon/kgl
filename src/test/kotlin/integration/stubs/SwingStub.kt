package io.kstud.khack.kgl.integration.stubs

import java.awt.Color
import java.awt.image.BufferedImage

class SwingStub : BaseStub() {
    override fun setup(width: Int, height: Int) {
        DisplayWindow.setup(width, height)
    }

    override fun start() {
        while (keepRunning) {
            val image = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)

            // Draw on the BufferedImage
            for (x in 0 until 200) {
                for (y in 0 until 200) {
                    if ((x + y) % 20 < 10) {
                        image.setRGB(x, y, Color.RED.rgb)
                    } else {
                        image.setRGB(x, y, Color.BLUE.rgb)
                    }
                }
            }

            DisplayWindow.repaint(image)

            try {
                Thread.sleep(16) // Approximately 60 fps
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        DisplayWindow.close()
    }
}

fun main() {
    DisplayWindow.setup(200, 200)

    while (DisplayWindow.isOpen()) {
        val image = BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB)

        // Draw on the BufferedImage
        for (x in 0 until 200) {
            for (y in 0 until 200) {
                if ((x + y) % 20 < 10) {
                    image.setRGB(x, y, Color.RED.rgb)
                } else {
                    image.setRGB(x, y, Color.BLUE.rgb)
                }
            }
        }

        DisplayWindow.repaint(image)

        try {
            Thread.sleep(16) // Approximately 60 fps
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    DisplayWindow.close()
}