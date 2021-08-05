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
internal class ItemRetrieveControllerTest {
    @Autowired
    private lateinit var itemCreateService: ItemCreateService

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `Should success to retrieve item with next`() {
        // given
        val item1 = runBlocking {
            itemCreateService.create(
                ItemCreateRequest(
                    name = "Apple Watch",
                    price = 1_000_000L
                ), audit = Audit.MOCK
            )
        }
        val item2 = runBlocking {
            itemCreateService.create(
                ItemCreateRequest(
                    name = "Apple Watch 2",
                    price = 1_000_000L
                ), audit = Audit.MOCK
            )
        }

        // when
        webTestClient.get()
            .uri("/api/v1/items/${item1.key}?with=next")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith {
                it.responseHeaders.print()
                it.responseBody.print()
            }
            .jsonPath("\$.status").isEqualTo("success")
            .jsonPath("\$.result.current.key").isEqualTo(item1.key)
            .jsonPath("\$.result.next.key").isEqualTo(item2.key)

    }

}