package day03

import java.io.File

fun main() {
    val input = getInputAsList("src/main/kotlin/day03/input.txt")
    println(sumAllNumsWithAdjacent(input))
    println(getGearRatios(input))
}

fun getInputAsList(path: String) = File(path).bufferedReader().readLines()

// Part 1

fun sumAllNumsWithAdjacent(input: List<String>): Int {
    var result = 0
    for (i in input.indices) {
        var j = 0
        while (j < input[i].length) {
            var end = j
            while (end < input[i].length && input[i][end].isDigit())
                ++end

            if (end == j) {
                ++j
                continue
            }
            if (checkAdjacent(input, i, j, end))
                result += input[i].substring(j, end).toInt()

            j = end
        }
    }
    return result
}

fun checkAdjacent(input: List<String>, line: Int, start: Int, end: Int): Boolean {
    for (i in line - 1..line + 1) {
        if (i < 0 || i >= input.size) continue
        for (j in start - 1..end) {
            if (j < 0 || j >= input[i].length) continue
            if (!input[i][j].isDigit() && input[i][j] != '.') return true
        }
    }
    return false
}

// Part 2

fun getGearRatios(input: List<String>): Int =
    getGearPositions(input)
        .map { getNumbersAroundGear(input, it.first, it.second) }
        .filter { it.size == 2 }
        .sumOf { it[0] * it[1] }


fun getGearPositions(input: List<String>): ArrayList<Pair<Int, Int>> {
    val gearPositions = ArrayList<Pair<Int, Int>>()

    for (i in input.indices)
        input[i].indices
            .filter { input[i][it] == '*' }
            .mapTo(gearPositions) { Pair(i, it) }

    return gearPositions
}

fun getNumbersAroundGear(input: List<String>, row: Int, col: Int): ArrayList<Int> {
    val gearNumbers = ArrayList<Int>()

    var x = if (row == 0) 0 else row - 1
    while (x >= 0 && x < input.size && x <= row + 1) {
        var y = if (col == 0) 0 else col - 1
        while (y >= 0 && y < input[x].length - 1 && y <= col + 1) {
            if (!input[x][y].isDigit()) {
                ++y
                continue
            }

            val temp = getNumberStartEnd(input[x], y)
            val number = input[x].substring(temp.first, temp.second).toInt()
            gearNumbers.add(number)
            y = temp.second + 1
        }
        ++x
    }

    return gearNumbers
}

fun getNumberStartEnd(line: String, pos: Int): Pair<Int, Int> {
    var start = pos
    var end = pos
    while (start > 0 && line[start - 1].isDigit()) --start
    while (end < line.length - 1 && line[end + 1].isDigit()) ++end

    return Pair(start, end + 1)
}
