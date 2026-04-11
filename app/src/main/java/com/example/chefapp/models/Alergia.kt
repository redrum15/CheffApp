package com.example.chefapp.models

data class Alergia(
    val id: Int,
    val nombre: String,
    val esPersonalizada: Boolean = false
)
