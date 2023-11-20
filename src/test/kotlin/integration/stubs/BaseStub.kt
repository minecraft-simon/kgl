package io.kstud.khack.kgl.integration.stubs

abstract class BaseStub : Stub {
    protected var keepRunning = true

    fun launch() {
        Thread {
            start()
        }.start()
    }

    fun stop() {
        keepRunning = false
    }

    fun isRunning(): Boolean {
        return keepRunning
    }

}