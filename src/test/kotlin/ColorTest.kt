package io.kstud.khack.kgl

import deprecated.units.color.KGLColor
import deprecated.units.color.hsl.hsl
import deprecated.units.color.rgb.rgb
import org.junit.jupiter.api.Test

internal class ColorTest {

    @Test
    fun colorTest() {
        val color = KGLColor.rgb(100, 100, 100).hsl()
        println(color)
    }

    @Test
    fun blackTest() {
        val color = KGLColor.rgb(0, 0, 0).hsl()
    }

}