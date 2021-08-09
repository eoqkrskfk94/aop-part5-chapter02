package com.mj.aop_part5_chapter02.presentation.list

import com.mj.aop_part5_chapter02.databinding.FragmentProductListBinding
import com.mj.aop_part5_chapter02.presentation.BaseFragment
import org.koin.android.ext.android.inject

internal class ProductListFragment: BaseFragment<ProductListViewModel, FragmentProductListBinding>() {

    companion object{
        const val TAG = "ProductListFragment"
    }

    override val viewModel by inject<ProductListViewModel>()

    override fun getViewBinding(): FragmentProductListBinding = FragmentProductListBinding.inflate(layoutInflater)

    override fun observeData() {
    }
}