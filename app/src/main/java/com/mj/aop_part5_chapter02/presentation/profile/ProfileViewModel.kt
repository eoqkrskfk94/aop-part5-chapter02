package com.mj.aop_part5_chapter02.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mj.aop_part5_chapter02.data.preference.PreferenceManager
import com.mj.aop_part5_chapter02.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.jvm.internal.Intrinsics

internal class ProfileViewModel(
    private val preferenceManager: PreferenceManager
): BaseViewModel() {

    private var _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.Unintialized)
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    override fun fetchData() = viewModelScope.launch {
        setState(ProfileState.Loading)
        preferenceManager.getIdToken()?.let {
            setState(
                ProfileState.Login(it)
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    private fun setState(state: ProfileState) {
        _profileStateLiveData.postValue(state)
    }

}