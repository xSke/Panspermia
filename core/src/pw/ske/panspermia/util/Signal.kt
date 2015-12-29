package pw.ske.panspermia.util

class Signal<T>() {
    data class Registration<T>(val priority: Int, val fn: (T) -> Boolean)
    val registrations = arrayListOf<Registration<T>>()

    fun add(fn: (T) -> Boolean) {
        add(0, fn)
    }

    fun add(priority: Int, fn: (T) -> Boolean) {
        registrations.add(Registration(priority, fn))
        registrations.sortBy { it.priority }
    }

    fun dispatch(t: T) {
        registrations.any {
            it.fn(t)
        }
    }
}