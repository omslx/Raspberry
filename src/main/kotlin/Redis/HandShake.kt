package ir.nayragames.Redis

import redis.clients.jedis.RedisClient

fun handShake (addr : String) = RedisClient.create("redis://$addr:6379")

