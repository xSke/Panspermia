package pw.ske.panspermia.util

class Signal<T>() {
    data class Registration<T>(val priority: Int, val fn: (T) -> Boolean)
    val registrations = arrayListOf<Registration<T>>()

    fun register(fn: (T) -> Boolean) {
        register(0, fn)
    }

    fun register(priority: Int, fn: (T) -> Boolean) {
        registrations.add(Registration(priority, fn))
        registrations.sortBy { it.priority }
    }

    fun dispatch(t: T) {
        registrations.any {
            val out = it.fn(t)
            if (out) {
                val x = 1
            }
            out
        }
    }
}