package com.example.counter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_NAME = "Counters"
const val TABLE_NAME = "counters"

@Database(entities = [Counter::class], version = 1, exportSchema = false)
abstract class CounterDatabase : RoomDatabase() {
    abstract fun counterDao(): CounterDao

    companion object {
        @Volatile
        private var Instance: CounterDatabase? = null

        fun getDatabase(context: Context): CounterDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CounterDatabase::class.java, DB_NAME)
                    .build()
                    .also { Instance = it }
            }
        }
    }

}