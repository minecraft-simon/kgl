package net.khack.debugging

import deprecated.ConcurrentHashSet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object Trace {

    private val seen = ConcurrentHashSet<String>()
    private val events: ConcurrentHashMap<String, Int> = ConcurrentHashMap()
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private val processingLock = AtomicBoolean(false)

    init {
        executor.scheduleAtFixedRate(::processEvents, 2000, 2000, TimeUnit.MILLISECONDS)
    }

    fun log(event: String) {
        if (!seen.contains(event)) {
            seen.add(event)
            val value = events.compute(event) { _, count -> (count ?: 0) + 1 }
            if (value == 1) {
                println("First: $event")
            }
        }
    }

    private fun processEvents() {
        if (processingLock.compareAndSet(false, true)) {
            try {
                if (events.isEmpty()) {
                    return
                }
                val stringBuilder = StringBuilder()
                events.forEach { (event, count) ->
                    stringBuilder.append("Trace: $event, IPS: $count\n")
                }
                println(stringBuilder.toString())
                events.clear()
            } finally {
                processingLock.set(false)
            }
        }
    }

}