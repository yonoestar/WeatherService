package com.example

import com.example.plugins.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import java.util.Timer
import kotlin.concurrent.fixedRateTimer

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {  }
    }
    routing {
        get("/weather/{location}") {
            val location = call.parameters["location"]
            if (location != null) {
                val data = RedisClient.getData("weather:$location")
                if (data != null) {
                    call.respondText(data, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Data not found for location $location")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Location parameter missing")
            }
        }
    }

    fixedRateTimer("WeatherService", true, 0L, 5 * 60 * 1000) {
        val locations = listOf("Santiago", "Zurich", "Auckland", "Sydney", "London", "Georgia")
        GlobalScope.launch {
            locations.forEach { location ->
                val data = WeatherService.fetchWeatherData(location)
                if (data != null) {
                    RedisClient.saveData("weather:$location", data)
                }
            }
        }
    }

}

