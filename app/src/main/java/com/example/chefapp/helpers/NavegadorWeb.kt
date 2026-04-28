package com.example.chefapp.helpers

class NavegadorWeb {
    var urlActual: String = "https://www.recetasgratis.net"
        private set
    var historial: MutableList<String> = mutableListOf()
        private set
    var accesosRapidos: List<Pair<String, String>> = listOf(
        Pair("🥘 RecetasGratis", "https://www.recetasgratis.net"),
        Pair("📺 Tasty",         "https://tasty.co"),
        Pair("⭐ TasteAtlas",    "https://www.tasteatlas.com"),
        Pair("🍳 Epicurious",    "https://www.epicurious.com")
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



    fun agregarAccesoRapido(nombre: String, url: String) {
        if (accesosRapidos.none { it.second == url }) {
            accesosRapidos = accesosRapidos + Pair(nombre, url)
        }
    }

    private fun agregarAlHistorial(url: String) {
        historial.remove(url)
        historial.add(0, url)
        if (historial.size > 5) historial.removeAt(historial.size - 1)
    }
}
