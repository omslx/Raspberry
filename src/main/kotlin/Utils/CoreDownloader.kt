package ir.nayragames.Utils

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject

object PaperUtils {

    private val client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()

    private const val JSON_URL = "https://gist.githubusercontent.com/osipxd/6119732e30059241c2192c4a8d2218d9/raw/1ebc973e802c6e8219cb32b331d6cc8ee62acb74/paper-versions.json"

    fun getter(version: String, versionsDir: String = "./versions") {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(JSON_URL))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            error("Failed to fetch JSON: HTTP ${response.statusCode()}")
        }

        val jsonObject = JSONObject(response.body())
        val versionsJson = jsonObject.getJSONObject("versions")

        if (!versionsJson.has(version)) {
            error("The JSON file does not contain the version: $version")
        }

        val downloadURL = versionsJson.getString(version)
        downloader(downloadURL, version, versionsDir)
    }

    private fun downloader(downloadURL: String, name: String, versionsDir: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(downloadURL))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream())

        if (response.statusCode() != 200) {
            error("Failed to download file: HTTP ${response.statusCode()}")
        }

        val outputFile = createDirectory(versionsDir, "$name.jar")

        response.body().use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}