package io.kstud.khack.kgl

import deprecated.Layer
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

internal class LayerTest {

    @Test
    fun testGetVertexDataWithStandardInput() {
        val layer = Layer(100.0, 200.0, 50.0, 75.0, 1.0)
        val screenWidth = 1920f
        val screenHeight = 1080f

        val expected = floatArrayOf(
            // Expected values calculated based on the getVertexData method logic
            ((2.0 * 100.0 / screenWidth) - 1).toFloat(), (1 - (2.0 * 200.0 / screenHeight)).toFloat(), 0f, 1f, 1f, 1f, 1f,
            ((2.0 * 100.0 / screenWidth) - 1).toFloat(), (1 - (2.0 * (200.0 + 75.0) / screenHeight)).toFloat(), 0f, 1f, 1f, 1f, 1f,
            ((2.0 * (100.0 + 50.0) / screenWidth) - 1).toFloat(), (1 - (2.0 * 200.0 / screenHeight)).toFloat(), 0f, 1f, 1f, 1f, 1f,
            ((2.0 * (100.0 + 50.0) / screenWidth) - 1).toFloat(), (1 - (2.0 * (200.0 + 75.0) / screenHeight)).toFloat(), 0f, 1f, 1f, 1f, 1f
        )

        assertArrayEquals(expected, layer.getRectangle(screenWidth, screenHeight).getVertexData())
    }

    @Test
    fun testGetVertexDataWithZeroScreenSize() {
        val layer = Layer(100.0, 200.0, 50.0, 75.0, 1.0)
        val screenWidth = 0f
        val screenHeight = 0f

        val expected = floatArrayOf(
            // Expected values with zero screen size (likely an array of NaNs due to division by zero)
            // This is a design decision: how should the function behave with a zero screen size?
        )

        assertArrayEquals(expected, layer.getRectangle(screenWidth, screenHeight).getVertexData())
    }

    // Additional tests can be implemented here similar to the above, 
    // covering edge cases, negative screen sizes, etc.
}
