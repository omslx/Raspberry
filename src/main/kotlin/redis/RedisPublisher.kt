package redis

import utils.logger
import redis.clients.jedis.Jedis

class RedisPublisher(
    private val address: String,
    private val port: Int,
    private val defaultChannel: String
) {
    fun publish(message: String, channel: String = defaultChannel) {
        try {
            Jedis(address, port).use { jedis ->
                val receiversCount = jedis.publish(channel, message)
                logger("Message sent to $channel ($receiversCount client(s) received it)", error = false)
            }
        } catch (e: Exception) {
            logger("Failed to publish message: ${e.stackTraceToString()}", error = true)
        }
    }
}