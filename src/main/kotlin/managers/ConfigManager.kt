package managers

import com.akuleshov7.ktoml.Toml
import config.AppConfig
import config.saveDefaultConfig
import utils.createDirectory
import utils.logger
import kotlinx.serialization.decodeFromString

object ConfigManager {
    val configFile = createDirectory("./", "config.toml")

    fun loadConfig(): AppConfig {
        if (!configFile.exists()) {
            logger("config.toml not found. Creating default file...", error = true)
            saveDefaultConfig()
            return AppConfig()
        }

        return try {
            Toml.decodeFromString<AppConfig>(configFile.readText())
        } catch (e: Exception) {
            logger("Error reading config, using default settings: ${e.message}", error = true)
            AppConfig()
        }
    }
}