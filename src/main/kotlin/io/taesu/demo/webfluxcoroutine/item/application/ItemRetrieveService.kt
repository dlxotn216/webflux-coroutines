package io.taesu.demo.webfluxcoroutine.item.application

import io.taesu.demo.webfluxcoroutine.item.domain.Item
import io.taesu.demo.webfluxcoroutine.item.domain.ItemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@Service
class ItemRetrieveService(private val itemRepository: ItemRepository) {
    suspend fun retrieve(key: Long): Item {
        // Thread.sleep(1000L) -> coroutine의 스레드가 멈추기 때문에 블로킹되어 당연히 2초가 걸림
        delay(1000L)
        return itemRepository.findById(key).awaitSingleOrNull()
            ?: throw IllegalArgumentException("$key does not exists")
    }
}