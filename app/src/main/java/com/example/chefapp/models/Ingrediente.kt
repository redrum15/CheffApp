package com.example.chefapp.models

data class Ingrediente(
    val id: Int,
    val nombre: String,
    var cantidad: Float,
    var unidadMedida: UnidadMedida
) {
    fun convertirUnidad(unidad: UnidadMedida) {
        if (unidad == unidadMedida) return
        cantidad = when (unidad) {
            UnidadMedida.ONZAS -> cantidad * 0.035274f
            UnidadMedida.GRAMOS -> cantidad / 0.035274f
        }
        unidadMedida = unidad
    }

    fun ajustarCantidad(factor: Float) {
        cantidad *= factor
    }
}
