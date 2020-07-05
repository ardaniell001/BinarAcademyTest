package com.binar.ardanil.configuration.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.binar.ardanil.configuration.Constant

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constant.Table.USER}")
    fun loadAllUserLiveData(): LiveData<List<UserDb>>

    @Insert
    suspend fun insertUser(user: UserDb?)

    @Query("DELETE FROM ${Constant.Table.USER} WHERE id=:userId")
    suspend fun deleteUser(userId: Int)

    @Query("DELETE FROM ${Constant.Table.USER}")
    suspend fun deleteAll()

}