package deprecated

fun createOrthoMatrix(width: Float, height: Float): FloatArray {
    val orthoMatrix = FloatArray(16)

    // Set orthographic matrix values
    orthoMatrix[0] = 2f / width
    orthoMatrix[5] = -2f / height // Invert Y
    orthoMatrix[10] = -1f // Depth between -1 and 1
    orthoMatrix[12] = -1f // Move origin to top left
    orthoMatrix[13] = 1f
    orthoMatrix[14] = 0f
    orthoMatrix[15] = 1f

    return orthoMatrix
}
