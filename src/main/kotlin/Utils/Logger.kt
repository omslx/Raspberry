package ir.nayragames.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Logger (message: String, level: String = "INFO"){

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val timestamp = LocalDateTime.now().format(formatter)
    val logFile = createDirectory("./logs", "$timestamp.log")
    val logEntry = "[$timestamp] [$level] $message\n"
    logFile.appendText(logEntry)

}