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
            
            [discord]
            discord_Token = "put your bot token here"
            discord_title = "Alert"
            discord_color = 0xeb34b7
            discord_guildID = "put your guild id here"
            discord_description = <nil>
            
        """.trimIndent()

    configFile.writeText(defaultConfigText)
}