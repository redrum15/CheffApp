package com.example.chefapp.helpers

import com.example.chefapp.models.Receta
import com.example.chefapp.models.Video

class ReproductorVideo {
    var videoActual: Video? = null
        private set
    var listaVideos: List<Video> = emptyList()
        private set
    var volumen: Int = 80
        private set

    fun seleccionarVideo(video: Video) {
        videoActual = video
    }

    fun cargarDesdeRecetas(recetas: List<Receta>) {
        listaVideos = recetas.flatMap { it.videos }
        if (listaVideos.isNotEmpty()) {
            videoActual = listaVideos.first()
        }
    }

    fun reproducir() { }
    fun pausar() { }
    fun siguiente() {
        val idx = listaVideos.indexOf(videoActual)
        if (idx in 0 until listaVideos.size - 1) {
            videoActual = listaVideos[idx + 1]
        }
    }

    fun anterior() {
        val idx = listaVideos.indexOf(videoActual)
        if (idx > 0) {
            videoActual = listaVideos[idx - 1]
        }
    }

    fun ajustarVolumen(nivel: Int) {
        volumen = nivel.coerceIn(0, 100)
    }
}
