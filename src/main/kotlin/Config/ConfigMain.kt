package ir.nayragames.Config


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val download: DownloadConfig = DownloadConfig(),
    val paths: PathsConfig = PathsConfig(),
    val discord: DiscordConfig = DiscordConfig()
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
@Serializable
data class DiscordConfig(
    @SerialName("discord_Token")
    val discordToken: String = "put your bot token here",

    @SerialName("discord_guildID")
    val guildID: String = "put your guild id here",

    @SerialName("discord_description")
    val description: String = "<nil>",

    @SerialName("discord_title")
    val title: String = "Alert",

    @SerialName("discord_color")
    val color: Int = 0xeb34b7
)

