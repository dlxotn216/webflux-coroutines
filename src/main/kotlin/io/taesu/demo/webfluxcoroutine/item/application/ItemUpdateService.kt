package io.taesu.demo.webfluxcoroutine.item.application

import io.taesu.demo.webfluxcoroutine.app.base.Audit
import io.taesu.demo.webfluxcoroutine.item.domain.Item
import io.taesu.demo.webfluxcoroutine.item.domain.ItemRepository
import io.taesu.demo.webfluxcoroutine.item.domain.toItem
import io.taesu.demo.webfluxcoroutine.item.interfaces.ItemUpdateRequest
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@Service
class ItemUpdateService(private val itemRepository: ItemRepository) {
    suspend fun update(itemKey: Long, request: ItemUpdateRequest, audit: Audit): Item {
        return itemRepository.save(request.toItem(itemKey, audit)).awaitSingle()
    }
}

fun ItemUpdateRequest.toItem(itemKey: Long, audit: Audit) =
    toItem(
        key = itemKey,
        name = name,
        price = price,
        audit = audit
    )