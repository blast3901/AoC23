package day02

import java.io.File

fun main() {
    val input = getInputAsList("src/main/kotlin/day02/input.txt")
    println(addValidGames(input))
    println(addPowerOfMinimumCubes(input))
}

fun getInputAsList(path: String) = File(path).bufferedReader().readLines()

// Part 1

fun addValidGames(input: List<String>): Int {
    var result = 0

    for (line in input) {
        var isValid = true
        val splittedLine = line.split(": ", "; ")
        val gameID = splittedLine[0].replace("Game ", "").toInt()

        for (part in splittedLine) {
            isValid = isPartValid(part)
            if (!isValid) break
        }

        if (isValid) result += gameID
    }

    return result
}

fun isPartValid(part: String): Boolean {
    if (part[0] == 'G') return true

    var red = 0
    var green = 0
    var blue = 0

    val oneColor = part.split(", ")

    for (s in oneColor) {
        if (s.contains("red"))
            red = s.substring(0, s.indexOf(" ")).toInt()
        else if (s.contains("green"))
            green = s.substring(0, s.indexOf(" ")).toInt()
        else
            blue = s.substring(0, s.indexOf(" ")).toInt()
    }

    return !(red > 12 || green > 13 || blue > 14)
}

// Part 2

fun addPowerOfMinimumCubes(input: List<String>): Int {
    var result = 0

    for (line in input) {
        val splittedLine = line.split(": ", "; ").drop(1)
        var red = 1
        var green = 1
        var blue = 1

        for (part in splittedLine) {
            val countArray = getColorCount(part)
            red = if (countArray[0] > red) countArray[0] else red
            green = if (countArray[1] > green) countArray[1] else green
            blue = if (countArray[2] > blue) countArray[2] else blue
        }

        result += red * green * blue
    }

    return result
}

fun getColorCount(part: String): IntArray {
    val countArray = intArrayOf(1, 1, 1)

    val oneColor = part.split(", ")

    for (s in oneColor) {
        if (s.contains("red"))
            countArray[0] = s.substring(0, s.indexOf(" ")).toInt()
        else if (s.contains("green"))
            countArray[1] = s.substring(0, s.indexOf(" ")).toInt()
        else
            countArray[2] = s.substring(0, s.indexOf(" ")).toInt()
    }

    return countArray
}