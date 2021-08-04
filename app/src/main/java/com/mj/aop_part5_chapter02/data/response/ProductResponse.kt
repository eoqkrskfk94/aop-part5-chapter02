package com.mj.aop_part5_chapter02.data.response

import com.mj.aop_part5_chapter02.data.entity.product.ProductEntity
import java.util.*

data class ProductResponse(
    val id: Long,
    val createdAt: Date,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
) {
    fun toEntity(): ProductEntity =
        ProductEntity(
            id = id,
            createdAt = createdAt,
            productName = productName,
            productPrice = productPrice,
            productImage = productImage,
            productType = productType,
            productIntroductionImage = productIntroductionImage
        )
}
