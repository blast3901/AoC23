package day04

import java.io.File

fun main() {
    val input = getInputAsList("src/main/kotlin/day04/input.txt")
    println(analyzeScratchcards(input))
    println(totalNumberOfCards(input))
}

fun getInputAsList(path: String) = File(path).bufferedReader().readLines()

// Part 1

fun analyzeScratchcards(input: List<String>): Int {
    val matches = ArrayList<Pair<List<Int>, List<Int>>>()
    input.forEach { matches.add(getMatches(it)) }
    return matches.sumOf { eval(it) }
}


fun getMatches(line: String): Pair<List<Int>, List<Int>> {
    val leftHalf = ArrayList<Int>()
    val rigthHalf = ArrayList<Int>()
    var left = true

    line.replace("Card +\\d+: +".toRegex(), "")
        .split("\\| +".toRegex())
        .map { it.split(" +".toRegex()) }
        .forEach {
            it.forEach { num ->
                when {
                    num == "" -> left = false
                    left -> leftHalf.add(num.toInt())
                    else -> rigthHalf.add(num.toInt())
                }
            }
        }

    return Pair(leftHalf, rigthHalf)
}

fun eval(line: Pair<List<Int>, List<Int>>): Int {
    var count = 0
    line.first.filter { line.second.contains(it) }
        .forEach { _ ->
            when (count) {
                0 -> count = 1
                else -> count *= 2
            }
        }

    return count
}

// Part 2

fun totalNumberOfCards(input: List<String>): Int {
    val formatedInput = ArrayList<Pair<List<Int>, List<Int>>>()
    input.forEach { formatedInput.add(getMatches(it)) }

    val matches = ArrayList<Int>()

    formatedInput.forEach {
        var count = 0
        it.second.filter { num -> it.first.contains(num) }.forEach { _ -> ++count }
        matches.add(count)
    }

    val temp = IntArray(matches.size).apply { fill(1) }
    matches.indices.asSequence().forEach { i ->
        (1..matches[i])
            .asSequence()
            .takeWhile { j -> i + j < temp.size }
            .forEach { j -> temp[i + j] += temp[i] }
    }

    return temp.sum()
}
