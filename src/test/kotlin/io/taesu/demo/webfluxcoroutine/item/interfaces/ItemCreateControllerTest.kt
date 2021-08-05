package io.taesu.demo.webfluxcoroutine.item.interfaces

import io.taesu.demo.webfluxcoroutine.item.application.ItemCreateService
import io.taesu.demo.webfluxcoroutine.item.domain.ItemRepository
import io.taesu.demo.webfluxcoroutine.print
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemCreateControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `Should success to create item`() {
        // given
        val body = """{
            |"name": "Apple Watch",
            |"price": 1000000
            |}""".trimMargin()
        // when
        webTestClient.post()
            .uri("/api/v1/items")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .consumeWith {
                it.responseHeaders.print()
                it.responseBody.print()
            }
            .jsonPath("\$.status").isEqualTo("success")
            .jsonPath("\$.result.key").exists()
            .jsonPath("\$.result.name").exists()

    }
}