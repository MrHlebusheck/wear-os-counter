package com.example.counter.database

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias Counters = List<Counter>

@Entity(tableName = TABLE_NAME)
data class Counter(
    val name: String,
    val value: Int = 0,
    val step: Int = 1,
    val target: Int? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)