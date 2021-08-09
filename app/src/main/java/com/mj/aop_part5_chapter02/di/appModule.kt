package com.mj.aop_part5_chapter02.di

import com.mj.aop_part5_chapter02.data.network.buildOkHttpClient
import com.mj.aop_part5_chapter02.data.network.provideGsonConverterFactory
import com.mj.aop_part5_chapter02.data.network.provideProductApiService
import com.mj.aop_part5_chapter02.data.network.provideProductRetrofit
import com.mj.aop_part5_chapter02.data.repository.DefaultProductRepository
import com.mj.aop_part5_chapter02.data.repository.ProductRepository
import com.mj.aop_part5_chapter02.domain.GetProductItemUserCase
import com.mj.aop_part5_chapter02.presentation.list.ProductListViewModel
import com.mj.aop_part5_chapter02.presentation.main.MainViewModel
import com.mj.aop_part5_chapter02.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //viewmodels
    viewModel { MainViewModel() }
    viewModel {ProductListViewModel()}
    viewModel {ProfileViewModel()}

    //Coroutine Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //UseCases
    factory { GetProductItemUserCase(get()) }

    //repositories
    single<ProductRepository> { DefaultProductRepository(get(), get()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductApiService(get()) }
    single { provideProductRetrofit(get(), get()) }



}