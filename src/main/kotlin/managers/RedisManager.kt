package managers

import ir.nayragames.Managers.ConfigManager
import ir.nayragames.Utils.logger
import redis.RedisPublisher
import redis.RedisSubscriber
import redis.clients.jedis.Jedis
import kotlin.concurrent.thread

class RedisManager {
    private val config = ConfigManager.loadConfig().redis

    val publisher = RedisPublisher(config.address, config.port, config.channel)
    val subscriber = RedisSubscriber()

    fun startListening() {
        logger("Connecting to Redis at ${config.address}:${config.port}...", error = false)

        thread(name = "Redis-Subscriber-Thread") {
            try {
                Jedis(config.address, config.port).use { jedis ->
                    jedis.subscribe(subscriber, config.channel)
                }
            } catch (e: Exception) {
                logger("Redis subscriber error: ${e.stackTraceToString()}", error = true)
            }
        }
    }

    fun stopListening() {
        if (subscriber.isSubscribed) {
            subscriber.unsubscribe()
        }
    }
}