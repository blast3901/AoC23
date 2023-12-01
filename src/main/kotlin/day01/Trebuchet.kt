package day01

import java.io.File

fun main() {
    val input = getInputAsList("src/main/kotlin/day01/input.txt")
    println(getSumOf(input, true))
    println(getSumOf(input, false))
}

fun getInputAsList(path: String) = File(path).bufferedReader().readLines()

fun getSumOf(input: List<String>, isFirstTask: Boolean) =
    if (isFirstTask)
        input.sumOf { getValue(it.replace("\\D".toRegex(), "")) }
    else
        input.sumOf { convertToIntString(it) }

// Part 1

fun getValue(nums: String): Int {
    return when (nums.length) {
        1 -> nums.plus(nums).toInt()
        2 -> nums.toInt()
        else -> nums[0].toString().plus(nums[nums.lastIndex]).toInt()
    }
}

// Part 2

fun convertToIntString(line: String): Int {
    var temp: String
    var result = ""
    val numbers = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    for (i in line.indices) {
        temp = line.substring(i)
        if (temp[0].isDigit())
            result = result.plus(temp[0])
        else
            numbers.indices
                .asSequence()
                .filter { temp.startsWith(numbers[it]) }
                .forEach { result = result.plus(it + 1) }
    }
    return getValue(result)
}
