package ir.nayragames.Managers

import ir.nayragames.Config.ConfigManager
import ir.nayragames.Utils.Logger
import ir.nayragames.Utils.PaperUtils
import ir.nayragames.Utils.createDirectory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject

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
            Logger("version $version already exists in $versionsDir!", error = false)
        } else {
            Logger("version $version not found, now downloading to $versionsDir...",error = false)
            try {
                PaperUtils.getter(version, versionsDir)
                Logger("version $version downloaded successfully.",error = false)
            } catch (e: Exception) {
                Logger("Failed to download version $version: ${e.message}",error = true)
            }
        }
    }
}