package com.bezubaan.app.feature.auth.domain.usecase

import com.bezubaan.app.core.common.Resource
import com.bezubaan.app.feature.auth.data.remote.LoginRequest
import com.bezubaan.app.feature.auth.data.remote.RegisterRequest
import com.bezubaan.app.feature.auth.domain.model.User
import com.bezubaan.app.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: LoginRequest): Resource<User> {
        return repository.login(request)
    }
}

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: RegisterRequest): Resource<User> {
        return repository.register(request)
    }
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}

data class AuthUseCases(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val logout: LogoutUseCase
)
