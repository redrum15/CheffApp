package com.example.chefapp.services

import com.example.chefapp.models.*

class SistemaRecomendacion {

    fun generarRecomendaciones(usuario: Usuario): List<Receta> {
        val todas = RecetaData.recetas
        val filtradas = filtrarPorRestricciones(todas, usuario)
        return priorizarPorGustos(filtradas, usuario.alimentosFavoritos.map {
            Alimento(it.id, it.nombre, it.tipo)
        })
    }

    fun filtrarPorRestricciones(recetas: List<Receta>, usuario: Usuario): List<Receta> {
        return recetas.filter { receta ->
            receta.filtrarPorRestricciones(usuario)
        }
    }

    fun excluirIngredientesRestringidos(
        recetas: List<Receta>,
        alergias: List<Alergia>
    ): List<Receta> {
        val nombresAlergias = alergias.map { it.nombre.lowercase() }
        return recetas.filter { receta ->
            receta.ingredientes.none { ingrediente ->
                nombresAlergias.any { alergia ->
                    ingrediente.nombre.lowercase().contains(alergia)
                }
            }
        }
    }

    fun priorizarPorGustos(
        recetas: List<Receta>,
        favoritos: List<Alimento>
    ): List<Receta> {
        val nombresFavoritos = favoritos.map { it.nombre.lowercase() }
        return recetas.sortedByDescending { receta ->
            receta.ingredientes.count { ingrediente ->
                nombresFavoritos.any { fav ->
                    ingrediente.nombre.lowercase().contains(fav)
                }
            }
        }
    }

    fun filtrarVideos(usuario: Usuario): List<Video> {
        val recetasRecomendadas = generarRecomendaciones(usuario)
        return recetasRecomendadas.flatMap { it.videos }
    }
}
