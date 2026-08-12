package usecase

import managers.RedisManager

fun useCase(){
    val redisManager = RedisManager()

    redisManager.startListening()

    redisManager.publisher.publish("Hello World!")
}