package ir.nayragames.Managers

import ir.nayragames.Utils.Logger
import ir.nayragames.Utils.PaperUtils
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject

fun downloadManager() {
    val url = "https://gist.githubusercontent.com/osipxd/6119732e30059241c2192c4a8d2218d9/raw/1ebc973e802c6e8219cb32b331d6cc8ee62acb74/paper-versions.json"

    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    val versionsJson = JSONObject(response.body()).getJSONObject("versions")

    val versionsDir = File("./versions")
    if (!versionsDir.exists()) {
        versionsDir.mkdirs()
    }

    for (version in versionsJson.keys()) {
        val jarFile = File(versionsDir, "$version.jar")

        if (jarFile.exists()) {
            Logger("version $version already exists")
        } else {
            Logger("version $version not found, downloading now...")
            println("version $version not found, downloading now...")
            try {
                PaperUtils.getter(version)
                Logger("version $version downloaded successfully.")
                println("version $version downloaded successfully.")
            } catch (e: Exception) {
                Logger("Error downloading version $version: ${e.message}")
                println("Error downloading version $version: ${e.message}")
            }
        }
    }
}