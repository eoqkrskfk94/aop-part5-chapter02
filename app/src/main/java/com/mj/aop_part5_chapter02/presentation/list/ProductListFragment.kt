package com.mj.aop_part5_chapter02.presentation.list

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import com.mj.aop_part5_chapter02.databinding.FragmentProductListBinding
import com.mj.aop_part5_chapter02.extension.toast
import com.mj.aop_part5_chapter02.presentation.BaseFragment
import com.mj.aop_part5_chapter02.presentation.adapter.ProductListAdapter
import org.koin.android.ext.android.inject

internal class ProductListFragment: BaseFragment<ProductListViewModel, FragmentProductListBinding>() {

    companion object{
        const val TAG = "ProductListFragment"
    }

    override val viewModel by inject<ProductListViewModel>()

    override fun getViewBinding(): FragmentProductListBinding = FragmentProductListBinding.inflate(layoutInflater)

    private val adapter = ProductListAdapter()

    private val startProductDetailForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

        }

    override fun observeData() = viewModel.productListStateLiveData.observe(this) {
        when(it) {
            is ProductListState.UnInitialized -> {
                initViews(binding)
            }
            is ProductListState.Loading -> {
                handleLoadingState()
            }
            is ProductListState.Success -> {
                handleSuccessState(it)
            }
            is ProductListState.Error -> {
                handleErrorState()
            }
        }


    }

    private fun initViews(binding: FragmentProductListBinding) = with(binding) {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun handleLoadingState() = with(binding){
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ProductListState.Success) = with(binding) {
        refreshLayout.isRefreshing = false

        if(state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                //startProductDetailForResult.launch()
                requireContext().toast("product entity $it")
            }

        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
    }
}