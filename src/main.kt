fun main() {
    val sampleEquation: String = "x^2 -x -1"
    println(findExponents(sampleEquation))
}

fun findExponents(eq: String): List<Char> {
    val words: List<String> = eq.split('+', '-').map { it.trim() }
    return words.map {
        when {
            "x^" in it -> it.last()
            "x" in it -> '1'
            else -> '0'
        }
    }
}