package io.kstud.khack.kgl.integration.stubs

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.WindowConstants

object DisplayWindow {
    private lateinit var frame: JFrame
    private lateinit var label: JLabel

    fun setup(width: Int, height: Int) {
        frame = JFrame().apply {
            defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
            addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent?) {
                    frame.dispose()
                }
            })
            setSize(width, height)
            setLocationRelativeTo(null)
        }

        label = JLabel().apply {
            icon = ImageIcon(BufferedImage(width, height, BufferedImage.TYPE_INT_RGB))
        }

        frame.contentPane.add(label)
        frame.isVisible = true
    }

    fun repaint(image: BufferedImage) {
        label.icon = ImageIcon(image)
        label.repaint()
        frame.repaint()
    }

    fun isOpen(): Boolean {
        return frame.isDisplayable
    }

    fun close() {
        frame.dispose()
    }

}