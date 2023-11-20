package deprecated.units.color

class KGLColor private constructor(
    val k: Float, // -1 = black, 0 = gray, 1 = white
    val g: Float, // -1 = green, 0 = no tint, 1 = red
    val l: Float  // -1 = blue,  0 = no tint, 1 = yellow
) {
    companion object {
        fun lab(l: Float, a: Float, b: Float): KGLColor {
            return KGLColor(l, a, b)
        }
    }

    override fun toString(): String {
        return "KGLColor(l=$k, a=$g, b=$l)"
    }
}