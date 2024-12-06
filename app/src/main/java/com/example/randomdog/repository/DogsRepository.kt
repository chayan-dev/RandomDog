package com.example.randomdog.repository

import com.example.randomdog.api.RandomDogsAPI
import com.example.randomdog.db.Dog
import com.example.randomdog.db.DogDao
import javax.inject.Inject

class DogsRepository @Inject constructor(
    private val dao: DogDao,
    private val api: RandomDogsAPI
) {
    suspend fun getRandomDog() = api.getRandomDog()

    suspend fun insertDog(dog: Dog) = dao.insert(dog)

    suspend fun getCount() = dao.getCount()

    suspend fun deleteOldestRecord() = dao.deleteOldest()

    suspend fun getAllDogs() = dao.getAllDogs()

    suspend fun clearDogsRecords() = dao.deleteAllData()
}