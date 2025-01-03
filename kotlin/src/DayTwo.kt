import java.io.BufferedReader
import java.nio.file.Path
import java.util.regex.Pattern
import kotlin.io.path.isReadable
import kotlin.io.path.reader
import kotlin.math.abs

fun getDayTwoFileContent(vararg strings: String): List<List<Int>> {
    //Throw IllegalArgumentException()
    require(strings.isNotEmpty()) { "Must have at least one argument" }
    // Check valid file
    val filePath = Path.of(strings.first(), *strings.slice(1 until strings.size).toTypedArray())
    if (!filePath.isReadable()) {
        error("File not found or is not readable")
    }
    // Regex to split lines on
    val regex = Pattern.compile("\\s")

    val reports = ArrayList<List<Int>>()
    // Try-with-resource: BufferedReader(InputStreamReader()) for good efficiency
    BufferedReader(filePath.reader()).use { bufferedReader ->
        bufferedReader.run {
            this.forEachLine {
                it.run {
                    val values = this.split(regex)
                    val intValues = values.map { s -> s.toInt() }
                    reports.addAll(listOf(intValues))
                }
            }
        }
    }
    return reports
}

fun countValidReports(reports: List<List<Int>>): Int {
    var score = 0
    for (report in reports) {
        if (isReportValid(report))
            score++
    }
    return score
}

fun countValidReportsPartTwo(reports: List<List<Int>>): Int {
    var score = 0
    for (report in reports) {
        for (i in report.indices) {
            val subReport = report.filterIndexed { index, _ -> index != i }
            if (isReportValid(subReport)) {
                score++
                break
            }
        }
    }
    return score
}


fun isReportValid(report: List<Int>): Boolean {
    if (report.isEmpty() || report.size == 1)
        return false
    if (report.distinct().size != report.size)
        return false
    if (report != report.sortedDescending() && report != report.sorted())
        return false
    for (i in 0..<(report.size - 1)) {
        val differenceBetweenLevels = abs(report[i] - report[i + 1])
        if (differenceBetweenLevels > 3)
            return false
    }
    return true
}


fun runDayTwo() {
    println("------------ Day 2 ------------")
    val reports = getDayTwoFileContent("kotlin", "resources", "daytwo", "data.txt")
    println(countValidReports(reports))
    println(countValidReportsPartTwo(reports))
    println("------------ Day 2 ------------")
}
