package ir.nayragames.Managers

import com.akuleshov7.ktoml.Toml
import ir.nayragames.Config.AppConfig
import ir.nayragames.Config.saveDefaultConfig
import ir.nayragames.Utils.Logger
import ir.nayragames.Utils.createDirectory
import kotlinx.serialization.decodeFromString

object ConfigManager {
    val configFile = createDirectory("./", "config.toml")

    fun loadConfig(): AppConfig {
        if (!configFile.exists()) {
            Logger("config.toml not found. Creating default file...", error = true)
            saveDefaultConfig()
            return AppConfig()
        }

        return try {
            Toml.decodeFromString<AppConfig>(configFile.readText())
        } catch (e: Exception) {
            Logger("Error reading config, using default settings: ${e.message}", error = true)
            AppConfig()
        }
    }
}