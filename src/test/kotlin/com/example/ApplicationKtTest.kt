package com.example

import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ApplicationKtTest {

    @Test
    fun testGetWeatherLocation() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/weather/{location}").apply {
            TODO("Please write your test here")
        }
    }
}