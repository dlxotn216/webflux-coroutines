package io.taesu.demo.webfluxcoroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by itaesu on 2021/07/23.
 *
 * @author Lee Tae Su
 * @version TBD
 * @since TBD
 */
@RestController
class ProductController(private val productRepository: ProductRepository) {


    @PostMapping("/api/v1/products")
    suspend fun create(@RequestBody request: ProductCreateRequest): ResponseEntity<Product> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                productRepository.save(
                    Product(
                        productId = request.id,
                        name = request.name
                    )
                )
            )
    }

    @GetMapping("/api/v1/products/{productKey}")
    suspend fun retrieve(@PathVariable productKey: Long): ResponseEntity<Product> {
        return ResponseEntity
            .ok(productRepository.findByKey(productKey))
    }

    @GetMapping("/api/v1/products")
    suspend fun retrieve(@RequestParam name: String): ResponseEntity<Flow<Product>> {
        return ResponseEntity
            .ok(productRepository.findAllByNameLike("%$name%"))
    }

    @GetMapping("/api/v1/products/{productKey}/with")
    suspend fun retrieveWith(@PathVariable productKey: Long): ResponseEntity<ProductRetrieveWithResponse> {
        return coroutineScope {
            val product: Deferred<Product> = async(start = CoroutineStart.LAZY) {
                productRepository.findByKey(productKey) ?: throw IllegalArgumentException("$productKey")
            }

            val productNext: Deferred<Product> = async(start = CoroutineStart.LAZY) {
                productRepository.findByKey(productKey + 1) ?: throw IllegalArgumentException("$productKey")
            }

            val productNameLike: Deferred<List<Product>> = async(start = CoroutineStart.LAZY) {
                productRepository.findAllByNameLike("%자%").toList()
            }

            ResponseEntity.ok(
                ProductRetrieveWithResponse(
                    product.await(),
                    productNext.await(),
                    productNameLike.await()
                )
            )
        }


    }
}

class ProductCreateRequest(
    val id: String,
    val name: String
)

class ProductRetrieveWithResponse(
    val product: Product,
    val product2: Product,
    val productNameLike: List<Product>
)