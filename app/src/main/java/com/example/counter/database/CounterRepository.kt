package com.example.counter.database

import kotlinx.coroutines.flow.Flow

class CounterRepository(private val counterDao: CounterDao) {
    val allCounters: Flow<Counters> = counterDao.getAllCounters()

    suspend fun createCounter(counter: Counter) {
        counterDao.createCounter(counter)
    }

    fun getCounterById(id: Int): Flow<Counter> {
        return counterDao.getCounterById(id)
    }

    suspend fun updateCounter(counter: Counter) {
        counterDao.updateCounter(counter)
    }

    suspend fun rename(counter: Counter, name: String) {
        counterDao.rename(counter.id, name)
    }

    suspend fun incrementCounter(counter: Counter) {
        counterDao.incrementCounter(counter.id)
    }

    suspend fun decrementCounter(counter: Counter) {
        counterDao.decrementCounter(counter.id)
    }

    suspend fun incrementStep(counter: Counter) {
        counterDao.incrementStep(counter.id)
    }

    suspend fun decrementStep(counter: Counter) {
        counterDao.decrementStep(counter.id)
    }

    suspend fun setTarget(counter: Counter, target: Int?) {
        counterDao.setTarget(counter.id, target)
    }

    suspend fun deleteCounter(counter: Counter) {
        counterDao.deleteCounter(counter)
    }
}