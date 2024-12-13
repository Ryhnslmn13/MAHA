package com.example.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecommendationViewModel : ViewModel() {
    private val repository = RecommendationRepository()

    private val _recommendation = MutableLiveData<RecommendationResponse?>()
    val recommendation: LiveData<RecommendationResponse?> get() = _recommendation

    fun fetchRecommendations(age: Int, height: Float, weight: Float, sex: String,
                             hypertension: Boolean, diabetes: Boolean) {
        viewModelScope.launch {
            val response = repository.getRecommendations(age, height, weight, sex, hypertension, diabetes)
            _recommendation.postValue(response)
        }
    }
}
