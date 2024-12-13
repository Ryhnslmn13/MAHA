package com.example.data

data class User(
    val age: String,
    val diabetes: String,
    val email: String,
    val gender: String,
    val height: String,
    val hypertension: String,
    val name: String,
    val weight: String
) {
    constructor() : this("", "", "", "", "", "", "", "")
}

