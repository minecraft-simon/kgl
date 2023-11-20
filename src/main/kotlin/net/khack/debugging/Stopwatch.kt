package net.khack.debugging

import java.util.concurrent.ConcurrentHashMap

object Stopwatch {

    private val stopwatches: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

    fun start(name: String) {
        stopwatches[name] = System.nanoTime()
    }

    fun stop(name: String, label: String = "") {
        val start = stopwatches[name]
        if (start == null) {
            println("Stopwatch $name not started")
            return
        }
        val elapsed = System.nanoTime() - start
        if (elapsed > 1000000) {
            println("Stopwatch $name $label took ${elapsed / 1000000}ms")
        } else if (elapsed > 1000) {
            println("Stopwatch $name $label took ${elapsed / 1000}μs")
        } else {
            println("Stopwatch $name $label took $elapsed ns")
        }
        stopwatches.remove(name)
    }

    fun lap(name: String, label: String) {
        val start = stopwatches[name]
        if (start == null) {
            println("Stopwatch $name not started")
            return
        }
        val elapsed = System.nanoTime() - start
        if (elapsed > 1000000) {
            println("Stopwatch $name $label took ${elapsed / 1000000}ms")
        } else if (elapsed > 1000) {
            println("Stopwatch $name $label took ${elapsed / 1000}μs")
        } else {
            println("Stopwatch $name $label took $elapsed ns")
        }
        stopwatches[name] = System.nanoTime()
    }

}