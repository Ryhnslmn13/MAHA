package com.example.api

class RecommendationRepository {
    suspend fun getRecommendations(
        age: Int, height: Float, weight: Float, sex: String,
        hypertension: Boolean, diabetes: Boolean
    ): RecommendationResponse? {
        val response = RetrofitClient.apiService.getRecommendations(
            age, height, weight, sex, hypertension, diabetes
        )
        return if (response.isSuccessful) response.body() else null
    }
}
