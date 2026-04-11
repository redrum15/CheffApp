package com.example.chefapp.models

data class Video(
    val id: Int,
    val nombre: String,
    val url: String,
    val duracion: Int,
    val thumbnail: String
) {
    fun reproducir() { /* delegado al reproductor */ }
    fun pausar() { /* delegado al reproductor */ }
    fun detener() { /* delegado al reproductor */ }
}
