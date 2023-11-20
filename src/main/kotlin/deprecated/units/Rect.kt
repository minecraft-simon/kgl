package deprecated.units

data class Rect(
    private val topLeft: Vertex,
    private val bottomLeft: Vertex,
    private val topRight: Vertex,
    private val bottomRight: Vertex
) {
    fun getVertices(): List<Vertex> = listOf(topLeft, bottomLeft, topRight, bottomRight)

    fun getVertexData(): FloatArray = floatArrayOf(
        topLeft.position.x, topLeft.position.y, topLeft.position.z, topLeft.color.red.toFloat(), topLeft.color.green.toFloat(), topLeft.color.blue.toFloat(), topLeft.color.alpha.toFloat(),
        bottomLeft.position.x, bottomLeft.position.y, bottomLeft.position.z, bottomLeft.color.red.toFloat(), bottomLeft.color.green.toFloat(), bottomLeft.color.blue.toFloat(), bottomLeft.color.alpha.toFloat(),
        topRight.position.x, topRight.position.y, topRight.position.z, topRight.color.red.toFloat(), topRight.color.green.toFloat(), topRight.color.blue.toFloat(), topRight.color.alpha.toFloat(),
        bottomRight.position.x, bottomRight.position.y, bottomRight.position.z, bottomRight.color.red.toFloat(), bottomRight.color.green.toFloat(), bottomRight.color.blue.toFloat(), bottomRight.color.alpha.toFloat(),
    )
}