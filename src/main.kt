/*
TODO: troche syfiascie wyglada w tym mainie wczytywanie po 2 razy dla obu wielomianow
*/
fun main() {
    val sampleEquation = "3x^3 + x^2 -x -1"
    val samplePolynomial = "x + 3"
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
    val reg = Regex("(?<=[+-])|(?=[+-])")
    val words: MutableList<String> = eq.split(reg).map { it.trim() }.toMutableList()
    var index = 0
    for (word in words) {
        if (word == "-") {
            words[index + 1] = "-" + words[index+1]
        }
        index += 1
    }
    words.removeAll(arrayListOf("+","-"))
    val list: MutableList<Int> = mutableListOf()
    for (word in words){
        if ("x" in word) {
            val indexOfX = word.indexOf('x')
            if (indexOfX != 0 && word[0] != '-') {
                val slice = word.slice(0 until indexOfX).toInt()
                list.add(slice)
            } else if (indexOfX != 0) {
                list.add(-1)
            } else {
                list.add(1)
            }
        } else {
            list.add(word.toInt())
        }
    }
    return list
    /* return words.map {
        when {
            'x' == it.first() -> 1
            '-' == it.first() -> it.slice(0..1).toInt()
            "-x" == it.slice(0..1) -> -1
            else -> it.first().toInt()
        }
    } */
}

//TODO: no to bedzie rozbudowane wiec zaczalem dzielic to na jakies mini funkcje nwm czy jest czytelne
//TODO: a no i nazewnictwo zmiennych do ogarniecia bo raczysko
fun calculateRemainder(exponents1: List<Int>, coefficients1: List<Int>, exponents2: List<Int>, coefficients2: List<Int>): String {
    val stoppingIndex = getStoppingIndex(exponents1, exponents2)
    var index = 0
    var copyCoeff2 = coefficients2.map {it.toDouble()}.toMutableList()
    val copyCoeff1 = coefficients1.map {it.toDouble()}.toMutableList()
    while (emptyTillStoppingIndex(copyCoeff1, stoppingIndex)) {
        println(copyCoeff1.indexOfFirst { it != 0.0 })
        val ratio = copyCoeff1[index] / copyCoeff2[0]
        println(copyCoeff1)
        copyCoeff2 = copyCoeff2.map { it * ratio }.toMutableList()
        println(copyCoeff2)
        subtract(copyCoeff1, copyCoeff2, index)
        index += 1
        copyCoeff2 = coefficients2.map {it.toDouble()}.toMutableList()
    }
    return copyCoeff1.toString()
}

fun getStoppingIndex(exponents1: List<Int>, exponents2: List<Int>): Int {
    val lastToDivide = exponents2.first()
    return exponents1.indexOfFirst { it == lastToDivide }
}

fun emptyTillStoppingIndex(coefficients1: List<Double>, index: Int): Boolean {
    return coefficients1.indexOfFirst { it != 0.0 } <= index
}

fun subtract(coefficients1: MutableList<Double>, coefficients2: MutableList<Double>, index: Int) {
    var ind = 0 + index
    for (co in coefficients2) {
        coefficients1[ind] -= co
        ind += 1
    }
}