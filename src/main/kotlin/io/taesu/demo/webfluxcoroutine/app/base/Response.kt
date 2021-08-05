package io.taesu.demo.webfluxcoroutine.app.base

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
class SuccessResponse(
    val result: Any,
    val status: String = "success",
    val message: String = "Request was succeed."
)

class FailResponse(
    val error: Map<String, String>,
    val status: String = "fail",
    val message: String = "Request was failed."
)