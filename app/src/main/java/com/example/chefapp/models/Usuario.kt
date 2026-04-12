package com.example.chefapp.models

data class Usuario(
    val id: Int,
    var nombre: String,
    var apellido: String,
    var email: String,
    var contrasena: String,
    var edad: Int,
    var peso: Float,
    var estatura: Float,
    var objetivoNutricional: ObjetivoNutricional = ObjetivoNutricional.MANTENER_PESO,
    val preferencias: MutableList<PreferenciaDietetica> = mutableListOf(),
    val alergias: MutableList<Alergia> = mutableListOf(),
    val alimentosFavoritos: MutableList<Alimento> = mutableListOf(),
    val alimentosNoDeseados: MutableList<Alimento> = mutableListOf(),
    val recetasFavoritas: MutableList<Receta> = mutableListOf()
) {
    fun editarPerfil() {  }

    fun visualizarPerfil(): Usuario = this

    fun seleccionarObjetivo(objetivo: ObjetivoNutricional) {
        objetivoNutricional = objetivo
    }

    fun agregarPreferencia(pref: PreferenciaDietetica) {
        if (!preferencias.contains(pref)) preferencias.add(pref)
    }

    fun eliminarPreferencia(pref: PreferenciaDietetica) {
        preferencias.remove(pref)
    }

    fun agregarAlergia(alergia: Alergia) {
        if (!alergias.contains(alergia)) alergias.add(alergia)
    }

    fun eliminarAlergia(alergia: Alergia) {
        alergias.remove(alergia)
    }

    fun agregarAlimentoFavorito(alimento: Alimento) {
        alimentosFavoritos.add(alimento)
    }

    fun agregarAlimentoNoDeseado(alimento: Alimento) {
        alimentosNoDeseados.add(alimento)
    }

    fun guardarRecetaFavorita(receta: Receta) {
        if (!recetasFavoritas.contains(receta)) recetasFavoritas.add(receta)
    }

    fun calificarReceta(receta: Receta, valor: Int) {
    }

    fun calcularIMC(): Float {
        return if (estatura > 0) peso / (estatura * estatura) else 0f
    }
}


object UsuarioActual {
    val usuario = Usuario(
        id = 1,
        nombre = "Jorge",
        apellido = "Martinez",
        email = "jorge.martinez@email.com",
        contrasena = "1234",
        edad = 38,
        peso = 65f,
        estatura = 1.79f,
        objetivoNutricional = ObjetivoNutricional.BAJAR_PESO,
        alergias = mutableListOf(
            Alergia(1, "Mariscos \uD83E\uDD90"),
            Alergia(2, "Lácteos \uD83E\uDD5B"),
            Alergia(3, "Frutos secos \uD83E\uDD5C")
        ),
        preferencias = mutableListOf(
            PreferenciaDietetica(1, TipoDieta.SIN_GLUTEN),
            PreferenciaDietetica(2, TipoDieta.SIN_LACTOSA)
        ),
        alimentosFavoritos = mutableListOf(
            Alimento(1, "Pasta 🍝", TipoAlimento.FAVORITO),
            Alimento(2, "Sushi 🍣", TipoAlimento.FAVORITO),
            Alimento(3, "Tacos 🌮", TipoAlimento.FAVORITO)
        ),
        alimentosNoDeseados = mutableListOf(
            Alimento(4, "Hígado 🥩", TipoAlimento.NO_DESEADO),
            Alimento(5, "Remolacha 🍠", TipoAlimento.NO_DESEADO)
        )
    )
}
