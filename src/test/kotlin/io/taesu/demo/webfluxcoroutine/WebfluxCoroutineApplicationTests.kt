package io.taesu.demo.webfluxcoroutine

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import java.nio.charset.StandardCharsets

@SpringBootTest
class WebfluxCoroutineApplicationTests {

    @Test
    fun contextLoads() {
    }

}


fun ByteArray?.print() = this?.let {
    println("---------response is-----------")
    println(String(it, StandardCharsets.UTF_8))
}

fun HttpHeaders?.print() = this?.let {
    println("---------response headers are-----------")
    this.forEach {
        println("${it.key} - ${it.value}")
    }
}