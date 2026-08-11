package ir.nayragames.Config


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

