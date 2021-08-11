package com.mj.aop_part5_chapter02.domain

import com.mj.aop_part5_chapter02.data.entity.product.ProductEntity
import com.mj.aop_part5_chapter02.data.repository.ProductRepository

class GetProductItemUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(productId: Long): ProductEntity? {
        return productRepository.getProductItem(productId)
    }

}