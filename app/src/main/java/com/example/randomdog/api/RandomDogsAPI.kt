package com.example.randomdog.api

import retrofit2.Response
import retrofit2.http.GET

interface RandomDogsAPI {

    @GET("breeds/image/random")
    suspend fun getRandomDog() : Response<RandomDogResponse>
}