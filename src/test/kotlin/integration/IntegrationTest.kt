package io.kstud.khack.kgl.integration

import io.kstud.khack.kgl.integration.stubs.MinecraftStub
import io.kstud.khack.kgl.integration.stubs.MinecraftStubNew
import io.kstud.khack.kgl.integration.stubs.SwingStub
import deprecated.units.color.KGLColor
import deprecated.units.color.hsl.hsl
import deprecated.units.color.rgb.rgb
import org.junit.jupiter.api.Test

internal class IntegrationTest {

    @Test
    fun testStubs() {
        val swingStub = SwingStub()
        swingStub.setup(200, 200)
        swingStub.launch()

        val minecraftStub = MinecraftStubNew()
        minecraftStub.setup(200, 200)
        minecraftStub.launch()

        while (swingStub.isRunning() && minecraftStub.isRunning()) {
            Thread.sleep(1000)
        }

    }

    @Test
    fun testMinecraftStub() {
        val minecraftStub = MinecraftStubNew()
        minecraftStub.setup(200, 200)
        minecraftStub.launch()

        while (minecraftStub.isRunning()) {
            Thread.sleep(1000)
        }
    }


}