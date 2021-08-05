package io.taesu.demo.webfluxcoroutine.item.interfaces

import io.taesu.demo.webfluxcoroutine.app.base.Audit
import io.taesu.demo.webfluxcoroutine.item.application.ItemCreateService
import io.taesu.demo.webfluxcoroutine.print
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
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
internal class ItemUpdateControllerTest {

    @Autowired
    private lateinit var itemCreateService: ItemCreateService

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `Should success to update item`() {
        // given
        val item = runBlocking {
            itemCreateService.create(
                ItemCreateRequest(
                    name = "Apple Watch",
                    price = 1_000_000L
                ), audit = Audit.MOCK
            )
        }
        val body = """{
            |"name": "Apple Watch 3",
            |"price": 1000000
            |}""".trimMargin()
        // when
        webTestClient.put()
            .uri("/api/v1/items/${item.key}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith {
                it.responseHeaders.print()
                it.responseBody.print()
            }
            .jsonPath("\$.status").isEqualTo("success")
            .jsonPath("\$.result.key").isEqualTo(item.key)
            .jsonPath("\$.result.name").isEqualTo("Apple Watch 3")
            .jsonPath("\$.result.price").isEqualTo("1000000")

    }

    /**
     * JPA와 다르게 UPDATE 방식이 set 방식으로 나간다.
     * UPDATE ITEM SET NAME = $1, PRICE = $2, CREATED_BY = $3, CREATED_AT = $4, MODIFIED_BY = $5, MODIFIED_AT = $6 WHERE ITEM.ITEM_KEY = $7
     */
    @Test
    fun `Should fail to update item when item key does not exists`() {
        // given
        val body = """{
            |"name": "Apple Watch 3",
            |"price": 1000000
            |}""".trimMargin()
        // when
        webTestClient.put()
            .uri("/api/v1/items/-9999")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .consumeWith {
                it.responseHeaders.print()
                it.responseBody.print()
            }
            .jsonPath("\$.status").isEqualTo("fail")

    }
}