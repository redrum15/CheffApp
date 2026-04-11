package com.example.chefapp.models

import java.util.Date

data class Calificacion(
    val id: Int,
    val valor: Int,
    val texto: String,
    val fecha: Date
)
