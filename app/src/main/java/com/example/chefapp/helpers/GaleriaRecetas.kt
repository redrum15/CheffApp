package com.example.chefapp.helpers

import com.example.chefapp.models.Receta
import com.example.chefapp.models.RecetaData
import com.example.chefapp.models.Usuario

class GaleriaRecetas {
    var recetas: List<Receta> = RecetaData.recetas
        private set
    var indiceActual: Int = 0
        private set

    fun mostrarGaleria() { }

    fun ampliarImagen(receta: Receta) { }

    fun siguiente(): Receta? {
        if (indiceActual < recetas.size - 1) {
            indiceActual++
        }
        return recetas.getOrNull(indiceActual)
    }

    fun anterior(): Receta? {
        if (indiceActual > 0) {
            indiceActual--
        }
        return recetas.getOrNull(indiceActual)
    }

    fun seleccionarReceta(receta: Receta) {
        val idx = recetas.indexOf(receta)
        if (idx >= 0) indiceActual = idx
    }

    fun filtrarPorPreferencias(usuario: Usuario): List<Receta> {
        return recetas.filter { it.filtrarPorRestricciones(usuario) }
    }

    fun filtrarPorAlergia(usuario: Usuario): List<Receta> {
        return recetas.filter { receta ->
            val alergiasNombres = usuario.alergias.map { it.nombre.lowercase() }
            receta.ingredientes.none { ingrediente ->
                alergiasNombres.any { alergia -> ingrediente.nombre.lowercase().contains(alergia) }
            }
        }
    }
}
