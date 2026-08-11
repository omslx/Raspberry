package ir.nayragames.Config

import ir.nayragames.Managers.ConfigManager.configFile

fun saveDefaultConfig() {
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