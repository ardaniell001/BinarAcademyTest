package com.binar.ardanil.configuration.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.ardanil.configuration.Constant

@Entity(
    tableName = Constant.Table.USER
)
class UserDb(@PrimaryKey val uuid: String,
             val id: Int,
             val email: String?,
             val firstName: String?,
             val lastName: String?,
             val avatar: String?
             )