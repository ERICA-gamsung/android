package com.erica.gamsung.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.login.domain.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) : ViewModel() {
        fun getLoginUrl(): String {
            val uuid = UUID.randomUUID().toString()
            loginRepository.saveUUID(uuid)
            return "https://gamsung.shop/oauth2/authorization/facebook?uuid=$uuid"
        }

        fun fetchAccessToken(onTokenReceived: (Boolean) -> Unit) {
            viewModelScope.launch {
                val uuid = loginRepository.getSavedUUID()
                uuid?.let {
                    loginRepository.fetchAccessToken(it)
                    val hasAccount = loginRepository.hasAccount()
                    onTokenReceived(hasAccount)
                }
            }
        }
    }
