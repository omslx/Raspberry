package redis

import utils.logger
import redis.clients.jedis.JedisPubSub

class RedisSubscriber : JedisPubSub() {

    override fun onMessage(channel: String?, message: String?) {
        if (channel == null || message == null) return
        logger("Received message from [$channel]: $message", error = false)

        // TODO: process
    }

    override fun onSubscribe(channel: String?, subscribedChannels: Int) {
        logger("Subscribed to channel: $channel", error = false)
    }

    override fun onUnsubscribe(channel: String?, subscribedChannels: Int) {
        logger("Unsubscribed from channel: $channel", error = false)
    }
}