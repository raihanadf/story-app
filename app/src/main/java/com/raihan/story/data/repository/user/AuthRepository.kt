package com.raihan.story.data.repository.user

import android.util.Log
import com.raihan.story.data.model.dto.auth.LoginRequest
import com.raihan.story.data.model.dto.auth.LoginResponse
import com.raihan.story.data.model.dto.auth.RegisterRequest
import com.raihan.story.data.model.dto.auth.RegisterResponse
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.network.StoryService
import com.raihan.story.utils.PreferenceManager
import com.raihan.story.utils.koinModules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules

interface AuthRepository {
    fun login(user: LoginRequest): Flow<ApiStatus<LoginResponse>>
    fun register(user: RegisterRequest): Flow<ApiStatus<RegisterResponse>>
    fun logout(): Boolean
}

class AuthRepositoryImpl(
    private val api: StoryService, private val prefs: PreferenceManager
) : AuthRepository {

    override fun login(user: LoginRequest): Flow<ApiStatus<LoginResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.login(user)
                if (!response.error) {
                    val user = response.loginResult
                    prefs.setLoginPref(user)

                    reloadKoinModules()
                    emit(ApiStatus.Success(response))
                } else {
                    emit(ApiStatus.Error(response.message))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }

    override fun register(user: RegisterRequest): Flow<ApiStatus<RegisterResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.register(user)
                if (!response.error) {
                    emit(ApiStatus.Success(response))
                } else {
                    emit(ApiStatus.Error(response.message))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }

    override fun logout(): Boolean {
        return try {
            prefs.clearAllPreferences()
            reloadKoinModules()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun reloadKoinModules() {
        unloadKoinModules(koinModules)
        loadKoinModules(koinModules)
    }

    companion object {
        const val TAG = "AuthRepo"
    }
}
