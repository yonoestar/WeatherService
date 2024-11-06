import redis.clients.jedis.Jedis

object RedisClient {
    private val redis = Jedis("localhost") // Asegúrate de que Redis esté ejecutándose en localhost

    fun saveData(key: String, value: String) {
        redis.set(key, value)
    }

    fun getData(key: String): String? {
        return redis.get(key)
    }

    fun saveLog(errorMessage: String) {
        val timestamp = System.currentTimeMillis()
        redis.rpush("error_logs", "$timestamp: $errorMessage")
    }
}