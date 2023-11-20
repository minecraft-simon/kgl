package deprecated.units.color.rgb

import deprecated.units.color.KGLColor

object RGB {
    fun fromKGLColor(kglColor: KGLColor): RGBColor {
        // Conversion from KGLColor to RGB
        // Placeholder for actual conversion logic
        return RGBColor(100, 100, 100) // Example values
    }

    fun toKGLColor(r: Int, g: Int, b: Int): KGLColor {
        // Conversion from RGB to KGLColor
        // Placeholder for actual conversion logic
        return KGLColor.lab(50f, 50f, 50f) // Example values
    }
}

data class RGBColor(val r: Int, val g: Int, val b: Int)

fun KGLColor.Companion.rgb(r: Int, g: Int, b: Int): KGLColor = RGB.toKGLColor(r, g, b)
fun KGLColor.rgb(): RGBColor = RGB.fromKGLColor(this)
fun RGBColor.toKGLColor(): KGLColor = RGB.toKGLColor(r, g, b)



