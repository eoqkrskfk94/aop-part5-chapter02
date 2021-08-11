package com.mj.aop_part5_chapter02.domain

import com.mj.aop_part5_chapter02.data.entity.product.ProductEntity
import com.mj.aop_part5_chapter02.data.repository.ProductRepository

class OrderProductItemUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke(productEntity: ProductEntity): Long {
        return productRepository.insertProductItem(productEntity)
    }

}