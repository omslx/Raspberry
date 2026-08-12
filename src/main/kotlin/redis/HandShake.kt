package ir.nayragames.Redis

import ir.nayragames.Managers.ConfigManager
import redis.clients.jedis.RedisClient

fun handShake () {

    val config = ConfigManager.loadConfig()
    val addr = config.redis.address
    val port = config.redis.port

    RedisClient.create("redis://$addr:$port")

}
