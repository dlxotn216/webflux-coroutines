package io.taesu.demo.webfluxcoroutine

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository

/**
 * Created by itaesu on 2021/07/23.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@Table("PRD_PRODUCT")
data class Product(
    @Id
    @Column("PRODUCT_KEY")
    val key: Long = 0L,

    @Column("PRODUCT_ID")
    val productId: String,

    @Column("NAME")
    val name: String
): Persistable<Long> {
    override fun getId(): Long = key

    override fun isNew(): Boolean = key == 0L

}

interface ProductRepository: ReactiveCrudRepository<Product, Long> {
    suspend fun save(product: Product): Product

    @FlowPreview
    fun findAllByNameLike(name: String): Flow<Product>

    suspend fun findByKey(key: Long): Product?

    suspend fun findById(id: String): Product?
}
