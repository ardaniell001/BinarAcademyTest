package com.binar.ardanil.configuration.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.ardanil.configuration.Constant
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 2,
    entities = [
        UserDb::class
    ],
    exportSchema = false
)

abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    Constant.Table.DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}