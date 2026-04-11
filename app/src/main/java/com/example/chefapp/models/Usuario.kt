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
    fun editarPerfil() { /* editar datos */ }

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
        // En una implementación real, se crearía una Calificacion y se asociaría
    }

    fun calcularIMC(): Float {
        return if (estatura > 0) peso / (estatura * estatura) else 0f
    }
}

// ── Usuario por defecto de la app ────────────────────────────────────────────
object UsuarioActual {
    val usuario = Usuario(
        id = 1,
        nombre = "Marco",
        apellido = "Ríos",
        email = "marco.rios@email.com",
        contrasena = "",
        edad = 28,
        peso = 72f,
        estatura = 1.75f,
        objetivoNutricional = ObjetivoNutricional.MANTENER_PESO,
        alergias = mutableListOf(),
        preferencias = mutableListOf()
    )
}
