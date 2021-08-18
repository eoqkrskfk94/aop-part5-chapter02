package com.mj.aop_part5_chapter02.presentation.profile

import android.app.Activity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.mj.aop_part5_chapter02.R
import com.mj.aop_part5_chapter02.databinding.FragmentProfileBinding
import com.mj.aop_part5_chapter02.extension.loadCenterCrop
import com.mj.aop_part5_chapter02.presentation.BaseFragment
import com.mj.aop_part5_chapter02.presentation.adapter.ProductListAdapter
import com.mj.aop_part5_chapter02.presentation.detail.ProductDetailActivity
import org.koin.android.ext.android.inject

internal class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    override val viewModel by inject<ProfileViewModel>()

    private val adapter = ProductListAdapter()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("352723616823-h3inpubdga8vp0h80svv00729vnuvftr.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    private val gsc by lazy {
        GoogleSignIn.getClient(requireActivity(), gso)
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    task.getResult(ApiException::class.java)?.let { account ->
                        Log.e(TAG, "firebaseAuthWithGoogle: ${account.id}")
                        viewModel.saveToken(account.idToken ?: throw Exception())

                    } ?: throw Exception()
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }

        }

    override fun observeData() = viewModel.profileStateLiveData.observe(this) {
        when (it) {
            is ProfileState.Unintialized -> initViews()
            is ProfileState.Loading -> handleLoadingState()
            is ProfileState.Login -> handleLoginState(it)
            is ProfileState.Success -> handleSuccessState(it)
            is ProfileState.Error -> handleErrorState()
        }
    }



    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        loginButton.setOnClickListener {
            signInGoogle()
        }

        logoutButton.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
        loginRequiredGroup.isGone = true
    }

    private fun handleSuccessState(state: ProfileState.Success) = with(binding) {
        progressBar.isGone = true
        when (state) {
            is ProfileState.Success.Registered -> {
                handleRegisterState(state)
            }
            is ProfileState.Success.NotRegistered -> {
                profileGroup.isGone = true
                loginRequiredGroup.isVisible = true
            }
        }
    }

    private fun handleRegisterState(state: ProfileState.Success.Registered) =with(binding) {
        profileGroup.isVisible = true
        loginRequiredGroup.isGone = true
        profileImageView.loadCenterCrop(state.profileImageUri.toString(), 60f)
        userNameTextView.text = state.userName

        if(state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                startActivity(
                    ProductDetailActivity.newIntent(requireContext(), it.id)
                )
            }
        }

    }

    private fun handleLoginState(state: ProfileState.Login) = with(binding) {
        progressBar.isVisible = true
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                println(task)
                if(task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {
                    viewModel.setUserInfo(null)
                }
            }
    }

    private fun handleErrorState() {
    }

    private fun signInGoogle() {
        val signInIntent = gsc.signInIntent
        loginLauncher.launch(signInIntent)

    }
}