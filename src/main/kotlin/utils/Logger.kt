package ir.nayragames.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun logger (message: String, level: String = "INFO", error: Boolean) {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val timestamp = LocalDateTime.now().format(formatter)
    val logFile = createDirectory("./logs", "$timestamp.log")
    val logEntry = "[$timestamp] [$level] $message\n"
    if (error)
        error(message)
    else
        println(logEntry)
    logFile.appendText(logEntry)
}