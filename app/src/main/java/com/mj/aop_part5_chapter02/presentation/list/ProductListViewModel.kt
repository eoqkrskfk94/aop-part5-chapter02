package com.mj.aop_part5_chapter02.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.aop_part5_chapter02.domain.GetProductListUserCase
import com.mj.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductListViewModel(
    private val getProductListUserCase: GetProductListUserCase
) : BaseViewModel() {

    private var _productListStateLiveData = MutableLiveData<ProductListState>(ProductListState.UnInitialized)
    val productListStateLiveData: LiveData<ProductListState> = _productListStateLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        setState(
            ProductListState.Loading
        )
        setState(
            ProductListState.Success(
                getProductListUserCase()
            )
        )
    }

    private fun setState(state: ProductListState) {
        _productListStateLiveData.postValue(state)
    }
}
