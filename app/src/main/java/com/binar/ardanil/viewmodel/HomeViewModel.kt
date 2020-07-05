package com.binar.ardanil.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.ardanil.configuration.database.UserDatabase
import com.binar.ardanil.configuration.database.UserDb
import com.binar.ardanil.configuration.database.UserRepository
import com.binar.ardanil.configuration.network.ApiManager
import com.binar.ardanil.model.User
import com.binar.ardanil.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(context: Context) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val errorFetchUsers = MutableLiveData<Int?>()
    private val fetchUsersResponse: MutableLiveData<UserResponse?> = MutableLiveData()
    val userList : LiveData<List<UserDb>>
    private val userRepository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(context, viewModelScope).userDao()
        userRepository = UserRepository(userDao)
        userList = userRepository.allUsers
    }

    fun isLoading(): LiveData<Boolean>? {
        return loading
    }

    fun getFetchUsers(): LiveData<UserResponse?>? {
        return fetchUsersResponse
    }

    private var fetchUsersCall: Call<UserResponse>? = null

    fun fetchUsersFromAPI() {
        loading.value = true
        fetchUsersCall = ApiManager().getService()?.getUsers("12")
        fetchUsersCall?.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(
                call: Call<UserResponse?>,
                response: Response<UserResponse?>
            ) {
                loading.value = false
                if (response.code() == 200) {
                    errorFetchUsers.value = null
                    val responses: UserResponse? = response.body()
                    fetchUsersResponse.setValue(responses)
                } else {
                    errorFetchUsers.setValue(response.code())
                }
            }

            override fun onFailure(
                call: Call<UserResponse?>,
                t: Throwable
            ) {
                loading.value = false
                errorFetchUsers.value = 500
            }
        })
    }

    fun saveUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.saveUser(user)
    }
}