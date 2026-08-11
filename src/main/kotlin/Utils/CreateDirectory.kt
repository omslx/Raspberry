package ir.nayragames.Utils

import java.io.File

fun createDirectory(paths: String, name: String): File {
    val file = File(paths, name)
    file.parentFile?.mkdirs()
    return file
}