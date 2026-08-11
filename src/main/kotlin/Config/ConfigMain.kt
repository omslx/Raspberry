package ir.nayragames.Config

import com.akuleshov7.ktoml.Toml
import ir.nayragames.Utils.Logger
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import java.io.File

@Serializable
data class AppConfig(
    val download: DownloadConfig = DownloadConfig(),
    val paths: PathsConfig = PathsConfig()
)

@Serializable
data class DownloadConfig(
    @SerialName("auto_download")
    val autoDownload: Boolean = true,

    @SerialName("max_concurrent")
    val maxConcurrent: Int = 3
)

@Serializable
data class PathsConfig(
    @SerialName("versions_dir")
    val versionsDir: String = "./versions"
)

object ConfigManager {
    private val configFile = File("config.toml")

    fun loadConfig(): AppConfig {
        if (!configFile.exists()) {
            Logger("config.toml not found. Creating default file...")
            saveDefaultConfig()
            return AppConfig()
        }

        return try {
            Toml.decodeFromString<AppConfig>(configFile.readText())
        } catch (e: Exception) {
            Logger("Error reading config, using default settings: ${e.message}")
            AppConfig()
        }
    }

    private fun saveDefaultConfig() {
        val defaultConfigText = """
            # Raspberry Config
            
            [download]
            auto_download = true
            max_concurrent = 3
            
            [paths]
            versions_dir = "./versions"
        """.trimIndent()

        configFile.writeText(defaultConfigText)
    }
}