package io.taesu.demo.webfluxcoroutine.item.interfaces

import io.taesu.demo.webfluxcoroutine.app.base.Audit
import io.taesu.demo.webfluxcoroutine.app.base.SuccessResponse
import io.taesu.demo.webfluxcoroutine.item.application.ItemUpdateService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@RestController
class ItemUpdateController(private val itemUpdateService: ItemUpdateService) {
    @PutMapping("/api/v1/items/{itemKey}")
    @ResponseStatus(code = HttpStatus.OK)
    suspend fun update(
        @PathVariable itemKey: Long,
        @RequestBody request: ItemUpdateRequest
    ): SuccessResponse {
        return with(itemUpdateService.update(itemKey, request, Audit.MOCK)) {
            SuccessResponse(
                result = ItemUpdateResponse(
                    key = key,
                    name = name,
                    price = price.toLong()
                )
            )
        }
    }
}

class ItemUpdateRequest(
    val name: String,
    val price: Long
)

class ItemUpdateResponse(
    val key: Long,
    val name: String,
    val price: Long
)