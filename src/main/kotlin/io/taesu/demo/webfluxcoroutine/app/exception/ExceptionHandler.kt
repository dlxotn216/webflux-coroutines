package io.taesu.demo.webfluxcoroutine.app.exception

import io.taesu.demo.webfluxcoroutine.app.base.FailResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ResponseEntity<FailResponse> {
        return ResponseEntity.badRequest().body(
            FailResponse(
                error = mapOf(
                    "errorCode" to "UNCATEGORIZED",
                    "message" to (e.message ?: "")
                )
            )
        )
    }
}