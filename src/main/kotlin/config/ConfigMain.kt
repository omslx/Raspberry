package config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val download: DownloadConfig = DownloadConfig(),
    val paths: PathsConfig = PathsConfig(),
    val discord: DiscordConfig = DiscordConfig(),
    val redis: RedisConfig = RedisConfig()
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
    @SerialName("token")
    val discordToken: String = "put your bot token here",

    @SerialName("guildID")
    val guildID: String = "put your guild id here",

    @SerialName("description")
    val description: String = "<nil>",

    @SerialName("title")
    val title: String = "Alert",

    @SerialName("color")
    val color: Int = 0xeb34b7
)

@Serializable
data class RedisConfig(
    @SerialName("address")
    val address: String = "localhost",

    @SerialName("port")
    val port: Int = 6379,

    @SerialName("channel")
    val channel: String = "raspberry"
)