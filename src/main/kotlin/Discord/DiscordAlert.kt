package ir.nayragames.Discord

import ir.nayragames.Managers.ConfigManager
import ir.nayragames.Utils.Logger
import ir.nayragames.Utils.Stats
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import java.awt.Color

fun alert(
    onlineStatus: Stats,
    reqFor: String
) {
    val config = ConfigManager.loadConfig()
    val guildID = config.discord.guildID
    val botToken = config.discord.discordToken
    val title = config.discord.title
    val description = config.discord.description
    val color = config.discord.color
    val channelId = guildID
    try {
        val jda = JDABuilder.createDefault(botToken).build()
        jda.awaitReady()

        val embed = EmbedBuilder().apply {
            setTitle(title)
            setDescription(description)
            setColor(Color(color))
            setThumbnail("https://minotar.net/helm/$reqFor/128.png")

            addField("reqFor", reqFor, true)
//            addField("enableStatus", enableStatus.toString(), true) // fek nakonam niaz beshe
            addField("onlineStatus", onlineStatus.toString(), true)

            setFooter("Raspberry Core")
        }.build()

        val channel = jda.getTextChannelById(channelId)
        if (channel != null) {
            channel.sendMessageEmbeds(embed).complete()
        } else {
            Logger("Channel not found with ID: $channelId", error = true)
        }

        jda.shutdown()

    } catch (e: Exception) {

        e.printStackTrace()
    }
}