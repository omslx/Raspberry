package ir.nayragames.Utils

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject

data class PaperData(
    val latest: String,
    val versions: Map<String, String>
)

object PaperUtils {

    fun getter(version: String) {
        val url = "https://gist.githubusercontent.com/osipxd/6119732e30059241c2192c4a8d2218d9/raw/1ebc973e802c6e8219cb32b331d6cc8ee62acb74/paper-versions.json"

        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        val jsonObject = JSONObject(response.body())
        val versionsJson = jsonObject.getJSONObject("versions")

        if (!versionsJson.has(version)) {
            error("The JSON file does not contain the version: $version")
        }

        val downloadURL = versionsJson.getString(version)

        downloader(downloadURL, version)
    }

    private fun downloader(downloadURL: String, name: String) {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(downloadURL))
            .GET()
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream())

        val outputFile = createDirectory("./versions","paper-$name.jar")

        response.body().use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}