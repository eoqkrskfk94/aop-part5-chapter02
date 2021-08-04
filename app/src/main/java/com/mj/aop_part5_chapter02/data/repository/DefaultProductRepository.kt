package com.mj.aop_part5_chapter02.data.repository

import com.mj.aop_part5_chapter02.data.entity.product.ProductEntity
import com.mj.aop_part5_chapter02.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val ioDispatcher: CoroutineDispatcher
): ProductRepository {

    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }


    override suspend fun insertProductList(ProductList: List<ProductEntity>) = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity) = withContext(ioDispatcher) {

        TODO("Not yet implemented")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity = _root_ide_package_.kotlinx.coroutines.withContext(ioDispatcher) {

        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {

        TODO("Not yet implemented")
    }

    override suspend fun deleteProductItem(id: Long) = withContext(ioDispatcher) {

        TODO("Not yet implemented")
    }
}