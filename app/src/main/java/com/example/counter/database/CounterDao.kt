package com.example.counter.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createCounter(counter: Counter)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllCounters(): Flow<Counters>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE id = :id")
    fun getCounterById(id: Int): Flow<Counter>

    @Update
    suspend fun updateCounter(counter: Counter)

    @Query("UPDATE $TABLE_NAME set name = :name WHERE id = :id")
    suspend fun rename(id: Int, name: String)

    @Query("UPDATE $TABLE_NAME SET value = value + step WHERE id = :id")
    suspend fun incrementCounter(id: Int)

    @Query(
        """UPDATE $TABLE_NAME SET value = 
        CASE WHEN value - step >= 0 
        THEN value - step 
        ELSE 0 END WHERE id = :id"""
    )
    suspend fun decrementCounter(id: Int)

    @Query("UPDATE $TABLE_NAME SET step = step + 1 WHERE id = :id")
    suspend fun incrementStep(id: Int)

    @Query(
        """UPDATE $TABLE_NAME SET step =
        CASE WHEN step - 1 >= 1
        THEN step - 1
        ELSE 1 END WHERE id = :id
    """
    )
    suspend fun decrementStep(id: Int)

    @Query(
        """UPDATE $TABLE_NAME SET target = 
        CASE WHEN :target > 0
        THEN :target
        ELSE NULL END WHERE id = :id
    """
    )
    suspend fun setTarget(id: Int, target: Int?)

    @Delete
    suspend fun deleteCounter(counter: Counter)
}