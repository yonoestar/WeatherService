import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay

object WeatherService {
    private val client = HttpClient()

    suspend fun fetchWeatherData(location: String): String?{
        return try {
            if (Math.random() < 0.2) throw RuntimeException("The API request failed")
            client.get()
        }
    }
}