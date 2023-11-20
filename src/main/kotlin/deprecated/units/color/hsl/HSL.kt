package deprecated.units.color.hsl

import deprecated.units.color.KGLColor

object HSL {
    fun fromKGLColor(kglColor: KGLColor): HSLColor {
        // Conversion from KGLColor to HSL
        // Placeholder for actual conversion logic
        return HSLColor(0.5f, 0.5f, 0.5f) // Example values
    }

    fun toKGLColor(h: Float, s: Float, l: Float): KGLColor {
        // Conversion from HSL to KGLColor
        // Placeholder for actual conversion logic
        return KGLColor.lab(60f, 60f, 60f) // Example values
    }
}

data class HSLColor(val h: Float, val s: Float, val l: Float)

fun KGLColor.hsl(): HSLColor = HSL.fromKGLColor(this)
fun HSLColor.toKGLColor(): KGLColor = HSL.toKGLColor(h, s, l)
