package com.binar.ardanil.configuration.database

import androidx.lifecycle.LiveData
import com.binar.ardanil.model.User
import java.util.*

class UserRepository(private val userDao: UserDao) {

    suspend fun saveUser(user: User) {
        val userDb = user.id?.let {
            UserDb(
                uuid = generateUuid(),
                id = user.id,
                email = user.email,
                firstName = user.first_name,
                lastName = user.last_name,
                avatar = user.avatar
            )
        }
        userDao.insertUser(userDb)
    }

    private fun generateUuid(): String = UUID.randomUUID().toString()

    val allUsers: LiveData<List<UserDb>> = userDao.loadAllUserLiveData()

}