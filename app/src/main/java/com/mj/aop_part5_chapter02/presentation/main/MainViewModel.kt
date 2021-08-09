package com.mj.aop_part5_chapter02.presentation.main

import androidx.lifecycle.viewModelScope
import com.mj.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {  }
}