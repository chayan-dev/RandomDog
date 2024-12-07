package com.example.randomdog.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomdog.api.RandomDogResponse
import com.example.randomdog.db.Dog
import com.example.randomdog.repository.DogsRepository
import com.example.randomdog.utils.Constants.Companion.CACHE_SIZE
import com.example.randomdog.utils.Constants.Companion.SUCCESS
import com.example.randomdog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    private val repository: DogsRepository
) : ViewModel() {

    val randomDog: MutableLiveData<Resource<RandomDogResponse>> = MutableLiveData()

    fun getRandomDog() = viewModelScope.launch(Dispatchers.IO)
    {
        randomDog.postValue(Resource.Loading())
        val response = repository.getRandomDog()
        if (response.isSuccessful && response.body()?.status == SUCCESS) {
            response.body()?.let {
                randomDog.postValue(Resource.Success(it))
            }
            response.body()?.let { dog ->
                insertDog(dog)
            }
        } else randomDog.postValue(Resource.Error(response.message()))
    }

    private suspend fun insertDog(dogResponse: RandomDogResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getCount() == CACHE_SIZE) {
                repository.deleteOldestRecord()
            }
            repository.insertDog(
                Dog(url = dogResponse.message)
            )
        }

}