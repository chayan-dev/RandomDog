package com.example.randomdog.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomdog.db.Dog
import com.example.randomdog.repository.DogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val repository: DogsRepository
) : ViewModel() {

    val allRecentDogs: MutableLiveData<List<Dog>> = MutableLiveData()

    fun getAllRecentDogs() = viewModelScope.launch(Dispatchers.IO)
    {
        allRecentDogs.postValue(repository.getAllDogs())
    }

    fun deleteAllRecentDogs() = viewModelScope.launch(Dispatchers.IO) {
        repository.clearDogsRecords()
        getAllRecentDogs()
    }
}