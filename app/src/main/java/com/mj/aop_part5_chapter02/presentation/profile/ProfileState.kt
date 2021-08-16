package com.mj.aop_part5_chapter02.presentation.profile

import android.net.Uri
import com.mj.aop_part5_chapter02.data.entity.product.ProductEntity

sealed class ProfileState {

    object Unintialized: ProfileState()

    object Loading: ProfileState()

    data class Login(
        val idToken: String
    ): ProfileState()

    sealed class Success: ProfileState() {

        data class Registered(
            val userName: String,
            val profileImageUri: Uri?,
            val productList: List<ProductEntity>
        ): Success()

        object NotRegistered: Success()
    }
}