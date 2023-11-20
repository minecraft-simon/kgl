package net.khack.debugging

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object ExpressionWatcher {
    private val observedValues: MutableMap<String, () -> Any?> = mutableMapOf()
    private val lastValues: MutableMap<String, Any?> = mutableMapOf()
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    fun <T> observe(tag: String, expression: () -> T) {
        observedValues[tag] = expression
        lastValues[tag] = expression.invoke()
        println("Now observing $tag. Current value: \u001B[38;2;255;165;0m${lastValues[tag]}\u001B[0m")
    }

    fun remove(tag: String) {
        observedValues.remove(tag)
        lastValues.remove(tag)
    }

    init {
        executor.scheduleAtFixedRate({
            observedValues.forEach { (tag, expression) ->
                val newValue = expression.invoke()
                val oldValue = lastValues[tag]

                if (newValue != oldValue) {
                    println("Value of $tag has changed to \u001B[38;2;255;165;0m$newValue\u001B[0m")
                    lastValues[tag] = newValue
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS)  // Check every 1 second
    }
}