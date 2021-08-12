package com.mj.aop_part5_chapter02.di

import com.mj.aop_part5_chapter02.data.db.provideDb
import com.mj.aop_part5_chapter02.data.db.provideToDoDao
import com.mj.aop_part5_chapter02.data.network.buildOkHttpClient
import com.mj.aop_part5_chapter02.data.network.provideGsonConverterFactory
import com.mj.aop_part5_chapter02.data.network.provideProductApiService
import com.mj.aop_part5_chapter02.data.network.provideProductRetrofit
import com.mj.aop_part5_chapter02.data.repository.DefaultProductRepository
import com.mj.aop_part5_chapter02.data.repository.ProductRepository
import com.mj.aop_part5_chapter02.domain.GetProductItemUseCase
import com.mj.aop_part5_chapter02.domain.GetProductListUseCase
import com.mj.aop_part5_chapter02.domain.OrderProductItemUseCase
import com.mj.aop_part5_chapter02.presentation.detail.ProductDetailViewModel
import com.mj.aop_part5_chapter02.presentation.list.ProductListViewModel
import com.mj.aop_part5_chapter02.presentation.main.MainViewModel
import com.mj.aop_part5_chapter02.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //viewmodels
    viewModel { MainViewModel() }
    viewModel {ProductListViewModel(get())}
    viewModel {ProfileViewModel()}
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get())}

    //Coroutine Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //UseCases
    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }

    //repositories
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductApiService(get()) }
    single { provideProductRetrofit(get(), get()) }

    //database
    single { provideDb(androidContext()) }
    single { provideToDoDao(get()) }




}