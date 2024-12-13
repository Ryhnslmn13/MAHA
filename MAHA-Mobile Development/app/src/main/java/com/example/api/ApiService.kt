package com.example.api


import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response



interface ApiService {
    @GET("recommender")
    suspend fun getRecommendations(
        @Query("age") age: Int,
        @Query("height") height: Float,
        @Query("weight") weight: Float,
        @Query("sex") sex: String,
        @Query("hypertension") hypertension: Boolean,
        @Query("diabetes") diabetes: Boolean
    ): Response<RecommendationResponse>
}