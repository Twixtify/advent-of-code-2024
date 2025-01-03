import java.io.BufferedReader
import java.nio.file.Path
import java.util.*
import java.util.regex.Pattern
import kotlin.io.path.isReadable
import kotlin.io.path.reader
import kotlin.math.abs

/**
 * Get content of day one file.
 * Data on left column and right column returned as a pair.
 */
fun getFileContent(vararg strings: String): Pair<List<Int>, List<Int>> {
    //Throw IllegalArgumentException()
    require(strings.isNotEmpty()) { "Must have at least one argument" }
    // Check valid file
    val filePath = Path.of(strings.first(), *strings.slice(1 until strings.size).toTypedArray())
    if (!filePath.isReadable()) {
        error("File not found or is not readable")
    }
    // Regex to split lines on
    val regex = Pattern.compile("\\s")

    val leftList = ArrayList<Int>()
    val rightList = ArrayList<Int>()
    // Try-with-resource: BufferedReader(InputStreamReader()) for good efficiency
    BufferedReader(filePath.reader()).use { bufferedReader ->
        bufferedReader.run {
            this.forEachLine {
                it.run {
                    val values = this.split(regex)
                    leftList.add(Integer.parseInt(values.first()))
                    rightList.add(Integer.parseInt(values.last()))
                }
            }
        }
    }
    return Pair(leftList, rightList)
}

fun getTotalDistance(leftList: List<Int>, rightList: List<Int>): Int {
    if (leftList.size != rightList.size) error("Lists must be of equal size.")
    val sortedLeftList = leftList.sortedDescending()
    val sortedRightList = rightList.sortedDescending()
    var totalDistance = 0
    sortedLeftList.forEachIndexed { i, value ->
        run {
            totalDistance += abs(value - sortedRightList[i])
        }
    }
    return totalDistance
}

fun getSimilarityScore(): Int {
    var score = 0
    val content = getFileContent("kotlin", "resources", "dayone", "data.txt")
    for (item in content.first) {
        score += item * Collections.frequency(content.second, item)
    }
    return score
}

fun runDayOne() {
    println("------------ Day 1 ------------")
    val content = getFileContent("kotlin", "resources", "dayone", "data.txt")
    println(getTotalDistance(content.first, content.second))
    println(getSimilarityScore())
    println("------------ Day 1 ------------")
}