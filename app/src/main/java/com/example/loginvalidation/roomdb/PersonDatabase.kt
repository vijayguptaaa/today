
package com.example.loginvalidation.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        private var INSTANCE: PersonDatabase? = null


        fun getInstance(context: Context): PersonDatabase {
            if (INSTANCE == null) {

                    INSTANCE = Room
                        .databaseBuilder(context.applicationContext,PersonDatabase::class.java,"person_db")
                        .build()
            }
            return INSTANCE!!
        }
    }
}