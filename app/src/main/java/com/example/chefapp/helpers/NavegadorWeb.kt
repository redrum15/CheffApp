package com.example.chefapp.helpers

class NavegadorWeb {
    var urlActual: String = "https://www.recetasgratis.net"
        private set
    var historial: MutableList<String> = mutableListOf()
        private set
    var accesosRapidos: List<String> = listOf(
        "https://www.recetasgratis.net",
        "https://tasty.co",
        "https://www.tasteatlas.com",
        "https://www.epicurious.com"
    )
        private set

    fun cargarPagina(url: String) {
        val urlFinal = if (!url.startsWith("http")) "https://$url" else url
        urlActual = urlFinal
        agregarAlHistorial(urlFinal)
    }

    fun navegarUrl(url: String) {
        cargarPagina(url)
    }

    fun mostrarHistorial(): List<String> = historial.toList()

    fun atras() { }
    fun adelante() { }
    fun recargar() { }

    fun agregarAccesoRapido(url: String) {
        if (!accesosRapidos.contains(url)) {
            accesosRapidos = accesosRapidos + url
        }
    }

    private fun agregarAlHistorial(url: String) {
        historial.remove(url)
        historial.add(0, url)
        if (historial.size > 5) historial.removeAt(historial.size - 1)
    }
}
