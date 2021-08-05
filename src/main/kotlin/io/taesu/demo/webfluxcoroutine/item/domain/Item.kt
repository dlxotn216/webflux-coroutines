package io.taesu.demo.webfluxcoroutine.item.domain

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
@Table("ITEM")
data class Item(
    @Id
    @Column("ITEM_KEY")
    val key: Long = 0L,

    @Column("NAME")
    val name: String,

    @Column("PRICE")
    val price: BigDecimal,

    @Column("CREATED_BY")
    var createdBy: Long,

    @Column("CREATED_AT")
    var createdAt: LocalDateTime,

    @Column("MODIFIED_BY")
    var modifiedBy: Long,

    @Column("MODIFIED_AT")
    var modifiedAt: LocalDateTime,
): Persistable<Long> {
    override fun getId(): Long = key

    override fun isNew(): Boolean = key == 0L
}

interface ItemRepository: ReactiveCrudRepository<Item, Long>