package io.taesu.demo.webfluxcoroutine.item.domain

import io.taesu.demo.webfluxcoroutine.app.base.Audit
import java.math.BigDecimal

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */


fun toItem(key: Long = 0L, name: String, price: Long, audit: Audit): Item =
    Item(
        key = key,
        name = name,
        price = BigDecimal.valueOf(price),
        createdBy = audit.createdBy,
        createdAt = audit.createdAt,
        modifiedBy = audit.modifiedBy,
        modifiedAt = audit.modifiedAt,
    )