package pw.ske.panspermia.stat

data class Stat<T>(val name: String, var level: Int, val maxLevel: Int, val levelPrices: List<Int>, val levelValues: List<T>, val startLevel: Int = level) {
    val nextLevel: Int get() {
        return level + 1
    }

    val nextLevelPrice: Int get() {
        return levelPrices[level]
    }

    val value: T get() {
        return levelValues[level]
    }
}