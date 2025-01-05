import java.io.BufferedReader
import java.nio.file.Path
import kotlin.io.path.isReadable
import kotlin.io.path.reader

fun getFileContentDayThree(vararg strings: String): List<String> {
    require(strings.isNotEmpty()) { "Must have at least one argument" }
    val filePath = Path.of(strings.first(), *strings.slice(1 until strings.size).toTypedArray())
    if (!filePath.isReadable()) {
        error("File not found or is not readable")
    }
    // Regex for patterns like mul(1,2)
    val regex = Regex("mul\\(\\d+,\\d+\\)+")

    val multiplications = ArrayList<String>()
    BufferedReader(filePath.reader()).use { bufferedReader ->
        bufferedReader.run {
            this.forEachLine {
                it.run {
                    val matches = regex.findAll(this)
                    for (match in matches) multiplications.add(match.value)
                }
            }
        }
    }
    return multiplications
}

fun runDayThree() {
    println("------------ Day 3 ------------")
    val result = getFileContentDayThree("kotlin", "resources", "daythree", "data.txt")
    println("------------ Day 3 ------------")
}