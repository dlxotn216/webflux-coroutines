package io.taesu.demo.webfluxcoroutine.order.domain

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Created by itaesu on 2021/08/05.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@Table("ORDER")
data class Order(
    @Id @Column("ORDER_KEY") val key: Long = 0L,
    @Column("STATUS") val status: OrderStatus,
    @Column("CREATED_BY") var createdBy: Long,
    @Column("CREATED_AT") var createdAt: LocalDateTime,
    @Column("MODIFIED_BY") var modifiedBy: Long,
    @Column("MODIFIED_AT") var modifiedAt: LocalDateTime,
): Persistable<Long> {
    override fun getId(): Long = key

    override fun isNew(): Boolean = key == 0L
}

enum class OrderStatus(
    val code: String
) {
    CREATED("CREATED"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED"),
}

interface OrderRepository: ReactiveCrudRepository<Order, Long>

@Table("ORDER_LINE")
data class OrderLine(
    @Id @Column("ORDER_LINE_KEY") val key: Long = 0L,
    @Column("ORDER_KEY") val orderKey: Long,
    @Column("ITEM_KEY") val itemKey: Long,
    @Column("QUANTITY") val quantity: Int,
    @Column("PRICE") val price: BigDecimal,
    @Column("CREATED_BY") var createdBy: Long,
    @Column("CREATED_AT") var createdAt: LocalDateTime,
    @Column("MODIFIED_BY") var modifiedBy: Long,
    @Column("MODIFIED_AT") var modifiedAt: LocalDateTime,
): Persistable<Long> {
    override fun getId(): Long = key

    override fun isNew(): Boolean = key == 0L
}

interface OrderLineRepository: ReactiveCrudRepository<OrderLine, Long>