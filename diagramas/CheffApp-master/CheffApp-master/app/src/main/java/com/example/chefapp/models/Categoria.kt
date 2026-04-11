package com.example.chefapp.models

data class Categoria(
    val id: Int,
    val nombre: String,
    val descripcion: String
) {
    fun getRecetas(todasLasRecetas: List<Receta>): List<Receta> {
        return todasLasRecetas.filter { it.categoria == this }
    }
}
