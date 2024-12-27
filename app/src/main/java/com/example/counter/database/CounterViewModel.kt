package com.example.counter.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CounterViewModel(private val repository: CounterRepository) : ViewModel() {
    val allCounters: Flow<Counters> = repository.allCounters

    fun createCounter(name: String) {
        viewModelScope.launch {
            repository.createCounter(Counter(name))
        }
    }

    fun counter(id: Int): Flow<Counter> {
        return repository.getCounterById(id)
    }

    fun updateCounter(counter: Counter) {
        viewModelScope.launch {
            repository.updateCounter(counter)
        }
    }

    fun renameCounter(counter: Counter, name: String) {
        viewModelScope.launch {
            repository.rename(counter, name)
        }
    }

    fun incrementCounter(counter: Counter) {
        viewModelScope.launch {
            repository.incrementCounter(counter)
        }
    }

    fun decrementCounter(counter: Counter) {
        viewModelScope.launch {
            repository.decrementCounter(counter)
        }
    }

    fun incrementStep(counter: Counter) {
        viewModelScope.launch {
            repository.incrementStep(counter)
        }
    }

    fun decrementStep(counter: Counter) {
        viewModelScope.launch {
            repository.decrementStep(counter)
        }
    }

    fun setTarget(counter: Counter, target: Int?) {
        viewModelScope.launch {
            repository.setTarget(counter, target)
        }
    }

    fun deleteCounter(counter: Counter) {
        viewModelScope.launch {
            repository.deleteCounter(counter)
        }
    }
}

class CounterViewModelFactory(private val repository: CounterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
            @Suppress("UNCHEKED_CAST")
            return CounterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}