package deprecated.units

interface Shader {
    fun loadSource(): String
    fun compile(): Int
}

