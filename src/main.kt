/*
TODO: troche syfiascie wyglada w tym mainie wczytywanie po 2 razy dla obu wielomianow
*/
fun main() {
    val sampleEquation: String = "3x^3 + x^2 -x -1"
    val samplePolynomial: String = "x + 3"
    val exponentsEq = findExponents(sampleEquation)
    val coefficientsEq = findCoefficients(sampleEquation)
    val exponentsPoly = findExponents(samplePolynomial)
    val coefficientsPoly = findCoefficients(samplePolynomial)
    println(calculateRemainder(exponentsEq, coefficientsEq, exponentsPoly, coefficientsPoly))
}

//TODO: nwm czy chcemy inty czy chary i czy to ma znaczenie w tym momencie w sumie
fun findExponents(eq: String): List<Int> {
    val words: List<String> = eq.split('+', '-').map { it.trim() }
    return words.map {
        when {
            "x^" in it -> it.last().toString().toInt()
            "x" in it -> 1
            else -> 0
        }
    }
}

fun findCoefficients(eq: String): List<Int> {
    val words: List<String> = eq.split('+', '-').map { it.trim() }
    return words.map {
        when {
            'x' == it.first() -> 1
            else -> it.first().toString().toInt()
        }
    }
}

//TODO: no to bedzie rozbudowane wiec zaczalem dzielic to na jakies mini funkcje nwm czy jest czytelne
//TODO: a no i nazewnictwo zmiennych do ogarniecia bo raczysko
fun calculateRemainder(exponents1: List<Int>, coefficients1: List<Int>, exponents2: List<Int>, coefficients2: List<Int>): String {
    var remainder = ""
    val stoppingIndex = getStoppingIndex(exponents1, exponents2)
    var index = 0
    var copyCoeff2 = coefficients2.toMutableList()
    var copyCoeff1 = coefficients1.toMutableList()
    while (!emptyTillStoppingIndex(copyCoeff1, stoppingIndex)) {
        val ratio = copyCoeff1[index].toDouble() / coefficients2[0]
        println(copyCoeff1)
        copyCoeff2 = copyCoeff2.map { it * ratio }.toMutableList()
        println(copyCoeff2)
        subtract(copyCoeff1, copyCoeff2, index)
        index += 1
    }
    return copyCoeff1.toString()
}

fun getStoppingIndex(exponents1: List<Int>, exponents2: List<Int>): Int {
    val lastToDivide = exponents2.first()
    return exponents1.indexOfFirst { it == lastToDivide }
}

fun emptyTillStoppingIndex(exponents1: List<Int>, index: Int): Boolean {
    //println(exponents1.first { it != 0} <= index)
    return exponents1.first { it != 0 } <= index
}

fun subtract(coefficients1: MutableList<Int>, coefficients2: MutableList<Int>, index: Int) {
    var ind = 0
    for (co in coefficients2) {
        coefficients1[ind] -= co
        ind += 1
    }
    coefficients1.drop(0)
}