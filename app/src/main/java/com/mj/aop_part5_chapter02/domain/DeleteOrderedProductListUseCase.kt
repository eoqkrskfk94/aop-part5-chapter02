package com.mj.aop_part5_chapter02.domain

import com.mj.aop_part5_chapter02.data.repository.ProductRepository

class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
): UseCase {

    suspend operator fun invoke() {
        return productRepository.deleteAll()
    }

}