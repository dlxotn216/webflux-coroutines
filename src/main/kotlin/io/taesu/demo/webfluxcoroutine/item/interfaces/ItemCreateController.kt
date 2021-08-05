package io.taesu.demo.webfluxcoroutine.item.interfaces

import io.taesu.demo.webfluxcoroutine.app.base.Audit
import io.taesu.demo.webfluxcoroutine.app.base.SuccessResponse
import io.taesu.demo.webfluxcoroutine.item.application.ItemCreateService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
class ItemCreateController(private val itemCreateService: ItemCreateService) {
    @PostMapping("/api/v1/items")
    @ResponseStatus(code = HttpStatus.CREATED)
    suspend fun create(@RequestBody request: ItemCreateRequest): SuccessResponse {
        return with(itemCreateService.create(request, Audit.MOCK)) {
            SuccessResponse(
                result = ItemCreateResponse(
                    key = key,
                    name = name,
                    price = price.toLong()
                )
            )
        }
    }
}

class ItemCreateRequest(
    val name: String,
    val price: Long
)

class ItemCreateResponse(
    val key: Long,
    val name: String,
    val price: Long
)