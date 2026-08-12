package managers

import utils.PaperUtils
import utils.createDirectory
import utils.logger
import org.json.JSONObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.collections.iterator

fun downloadManager() {
    val config = ConfigManager.loadConfig()
    val versionsDir = config.paths.versionsDir

    val url = "https://gist.githubusercontent.com/osipxd/6119732e30059241c2192c4a8d2218d9/raw/1ebc973e802c6e8219cb32b331d6cc8ee62acb74/paper-versions.json"

    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    val versionsJson = JSONObject(response.body()).getJSONObject("versions")

    for (version in versionsJson.keys()) {
        val versionFile = createDirectory(versionsDir, "$version.jar")

        if (versionFile.exists()) {
            logger("version $version already exists in $versionsDir!", error = false)
        } else {
            logger("version $version not found, now downloading to $versionsDir...",error = false)
            try {
                PaperUtils.getter(version, versionsDir)
                logger("version $version downloaded successfully.",error = false)
            } catch (e: Exception) {
                logger("Failed to download version $version: ${e.message}",error = true)
            }
        }
    }
}