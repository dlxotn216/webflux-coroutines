package io.taesu.demo.webfluxcoroutine.app.base

import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
data class Audit(
    var createdBy: Long,
    var createdAt: LocalDateTime,
    var modifiedBy: Long,
    var modifiedAt: LocalDateTime,
) {
    companion object{
        val MOCK = Audit(-1L, LocalDateTime.MIN, -1L, LocalDateTime.MIN)
    }
}