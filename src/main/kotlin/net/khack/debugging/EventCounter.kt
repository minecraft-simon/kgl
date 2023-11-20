package net.khack.debugging

import java.util.concurrent.ConcurrentHashMap

object EventCounter {

    private val events: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

    fun event(event: String) {
        events.compute(event) { _, value -> value?.plus(1) ?: 1 }
    }

    fun dump() {
        events.forEach { (event, count) ->
            println("Event: $event, Count: $count")
        }
        events.clear()
    }

}