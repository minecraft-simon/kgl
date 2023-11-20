package deprecated

import java.util.*
import kotlin.collections.HashSet

class ConcurrentHashSet<T> {
    private val set = Collections.synchronizedSet(HashSet<T>())

    fun add(item: T): Boolean {
        synchronized(set) {
            return set.add(item)
        }
    }

    fun contains(item: T): Boolean {
        synchronized(set) {
            return set.contains(item)
        }
    }
}