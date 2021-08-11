package com.mj.aop_part5_chapter02.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.mj.aop_part5_chapter02.databinding.ActivityProductDetailBinding
import com.mj.aop_part5_chapter02.extension.load
import com.mj.aop_part5_chapter02.extension.loadCenterCrop
import com.mj.aop_part5_chapter02.extension.toast
import com.mj.aop_part5_chapter02.presentation.BaseActivity
import com.mj.aop_part5_chapter02.presentation.list.ProductListState
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

internal class ProductDetailActivity : BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding>() {

    companion object{
        const val PRODUCT_ID_KEY = "PRODUCT_ID_KEY"
        const val PRODUCT_ORDERED_RESULT_CODE = 99
        fun newIntent(context: Context, productId: Long) = Intent(context, ProductDetailActivity::class.java).apply {
            putExtra(PRODUCT_ID_KEY, productId)
        }
    }

    override val viewModel by inject<ProductDetailViewModel> {
        parametersOf(
            intent.getLongExtra(PRODUCT_ID_KEY, -1)
        )
    }

    override fun getViewBinding(): ActivityProductDetailBinding = ActivityProductDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun observeData() = viewModel.productDetailStateLiveData.observe(this) {
        when(it) {
            is ProductDetailState.Uninitialized -> {
                initViews()
            }
            is ProductDetailState.Loading -> {
                handleLoadingState()
            }
            is ProductDetailState.Success -> {
                handleSuccessState(it)
            }
            is ProductDetailState.Error -> {
                handleErrorState()
            }
            is ProductDetailState.Order -> {
                handleOrderState()
            }
        }
    }

    private fun initViews() = with(binding) {
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        title = ""
        toolbar.setNavigationOnClickListener {
            finish()
        }

        orderButton.setOnClickListener {
            viewModel.orderProduct()
        }
    }

    private fun handleLoadingState() =with(binding) {
        progressBar.isVisible = true
    }

    private fun handleSuccessState(state: ProductDetailState.Success) = with(binding) {
        progressBar.isGone = true
        val product = state.productEntity
        title = product.productName
        productCategoryTextView.text = product.productType
        productImageView.loadCenterCrop(product.productImage, 8f)
        productPriceTextView.text = product.productPrice.toString()
        introductionImageView.load(product.productIntroductionImage)
    }

    private fun handleErrorState() {
        toast("error")
        finish()
    }

    private fun handleOrderState() {
        setResult(PRODUCT_ORDERED_RESULT_CODE)
        toast("success")
        finish()
    }
}