package config

import managers.ConfigManager.configFile

fun saveDefaultConfig() {
    val defaultConfigText = """
            # Raspberry Config
            
            [download]
            auto_download = true
            max_concurrent = 3
            
            [paths]
            versions_dir = "./versions"
            
            [discord]
            token = "put your bot token here"
            title = "Alert"
            color = 0xeb34b7
            guildID = "put your guild id here"
            description = <nil>
            
            [redis]
            address = "localhost"
            port = 6379
    ##        password = ""  // not work rn
            channel = "raspberry"
            
        """.trimIndent()

    configFile.writeText(defaultConfigText)
}