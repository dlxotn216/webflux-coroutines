package io.taesu.demo.webfluxcoroutine.item.interfaces

import io.taesu.demo.webfluxcoroutine.app.base.SuccessResponse
import io.taesu.demo.webfluxcoroutine.item.application.ItemRetrieveService
import io.taesu.demo.webfluxcoroutine.item.domain.Item
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatus
import org.springframework.util.StopWatch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@RestController
class ItemRetrieveController(private val itemRetrieveService: ItemRetrieveService) {
    @GetMapping("/api/v1/items/{itemKey}", params = ["with=next"])
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun retrieveWithNext(@PathVariable itemKey: Long): SuccessResponse {
        // parallel decomposition
        val watch = StopWatch()
        watch.start()
        val result = coroutineScope {
            /*
            아래 코드는 바로 블로킹을 하기 때문에 문제가 있다.
            val current = async { itemRetrieveService.retrieve(itemKey) }.await().toRetrieveResponse()

            CompletableFuture stream을 map으로 join하는 것과 동일.
            Stream.of(future1, future2...)
                .map(CompletableFuture::join) // 하나씩 블로킹
                .collect(...);

            Stream.of(future1, future2...)
                .collect(...)               // 일단 태스크 발행 해서 병렬로 처리 하고
                .forEach(CompletableFuture::join)   // 루프돌면서 블로킹
             */

            val current = async { itemRetrieveService.retrieve(itemKey) }
            val next = async { itemRetrieveService.retrieve(itemKey + 1) }
            SuccessResponse(
                ItemWithNextRetrieveResponse(
                    current = current.await().toRetrieveResponse(),
                    next = next.await().toRetrieveResponse()
                )
            )
        }
        watch.stop()
        println("processed ${watch.totalTimeMillis}")
        return result
    }

}

class ItemWithNextRetrieveResponse(
    val current: ItemRetrieveResponse,
    val next: ItemRetrieveResponse,
)

class ItemRetrieveResponse(
    val key: Long,
    val name: String,
    val price: Long
)

private fun Item.toRetrieveResponse() =
    ItemRetrieveResponse(
        key = key,
        name = name,
        price = price.toLong(),
    )