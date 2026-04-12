package com.example.chefapp.models

data class Receta(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val dificultad: Dificultad,
    val porciones: Int,
    val calificacionPromedio: Float,
    val pasos: List<String>,
    val ingredientes: List<Ingrediente> = emptyList(),
    val videos: List<Video> = emptyList(),
    val calificaciones: List<Calificacion> = emptyList(),
    val categoria: Categoria? = null
) {
    fun visualizar() { }

    fun ajustarPorciones(nuevasPorciones: Int): Receta {
        if (nuevasPorciones <= 0 || porciones <= 0) return this
        val factor = nuevasPorciones.toFloat() / porciones
        val ingredientesAjustados = ingredientes.map { it.copy(cantidad = it.cantidad * factor) }
        return copy(porciones = nuevasPorciones, ingredientes = ingredientesAjustados)
    }

    fun cambiarUnidadMedida(unidad: UnidadMedida) {
        ingredientes.forEach { it.convertirUnidad(unidad) }
    }

    fun calcularIngredientes() { }

    fun calcularCalificacionPromedio(): Float {
        if (calificaciones.isEmpty()) return calificacionPromedio
        return calificaciones.map { it.valor }.average().toFloat()
    }

    fun filtrarPorRestricciones(usuario: Usuario): Boolean {
        val alergiasNombres = usuario.alergias.map { it.nombre.lowercase() }
        return ingredientes.none { ingrediente ->
            alergiasNombres.any { alergia -> ingrediente.nombre.lowercase().contains(alergia) }
        }
    }
}


object RecetaData {

    private val catItaliana = Categoria(1, "Italiana", "Cocina italiana clásica")
    private val catEnsalada = Categoria(2, "Ensalada", "Ensaladas frescas")
    private val catPostre = Categoria(3, "Postre", "Postres y dulces")
    private val catMexicana = Categoria(4, "Mexicana", "Cocina mexicana tradicional")
    private val catJaponesa = Categoria(5, "Japonesa", "Cocina japonesa")

    val categorias = listOf(catItaliana, catEnsalada, catPostre, catMexicana, catJaponesa)

    val recetas = listOf(
        Receta(
            id = 1,
            nombre = "Pasta Carbonara",
            descripcion = "Clásico romano con guanciale, pecorino, huevo y pimienta negra. Sin crema — la tradición manda.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",
            dificultad = Dificultad.MEDIO,
            porciones = 4,
            calificacionPromedio = 4.8f,
            pasos = listOf("Hervir pasta", "Preparar guanciale", "Mezclar huevos con pecorino", "Combinar todo"),
            ingredientes = listOf(
                Ingrediente(1, "Pasta", 200f, UnidadMedida.GRAMOS),
                Ingrediente(2, "Huevos", 4f, UnidadMedida.GRAMOS),
                Ingrediente(3, "Queso pecorino", 100f, UnidadMedida.GRAMOS),
                Ingrediente(4, "Guanciale", 150f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(1, "Pasta Carbonara", "D_2DBLAt57c", 600, "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg")),
            categoria = catItaliana
        ),
        Receta(
            id = 2,
            nombre = "Ensalada César",
            descripcion = "Lechuga romana, crutones crujientes, parmesano y aderezo César casero con anchoas.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/qq10wb1585908761.jpg",
            dificultad = Dificultad.FACIL,
            porciones = 4,
            calificacionPromedio = 4.6f,
            pasos = listOf("Lavar lechuga", "Preparar aderezo", "Tostar crutones", "Ensamblar"),
            ingredientes = listOf(
                Ingrediente(5, "Lechuga romana", 300f, UnidadMedida.GRAMOS),
                Ingrediente(6, "Parmesano", 50f, UnidadMedida.GRAMOS),
                Ingrediente(7, "Crutones", 100f, UnidadMedida.GRAMOS),
                Ingrediente(8, "Anchoas", 30f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(2, "Ensalada César", "UriKNqnrfMc", 450, "https://www.themealdb.com/images/media/meals/qq10wb1585908761.jpg")),
            categoria = catEnsalada
        ),
        Receta(
            id = 3,
            nombre = "Pizza Margarita",
            descripcion = "Masa artesanal, salsa de tomate San Marzano, mozzarella fresca y albahaca.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg",
            dificultad = Dificultad.MEDIO,
            porciones = 4,
            calificacionPromedio = 4.9f,
            pasos = listOf("Preparar masa", "Extender salsa", "Agregar mozzarella", "Hornear"),
            ingredientes = listOf(
                Ingrediente(9, "Harina", 300f, UnidadMedida.GRAMOS),
                Ingrediente(10, "Tomate San Marzano", 200f, UnidadMedida.GRAMOS),
                Ingrediente(11, "Mozzarella", 200f, UnidadMedida.GRAMOS),
                Ingrediente(12, "Albahaca", 10f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(3, "Pizza Margarita", "1-SJGQ2HBt8", 900, "https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg")),
            categoria = catItaliana
        ),
        Receta(
            id = 4,
            nombre = "Torta de Chocolate",
            descripcion = "Bizcocho húmedo de cacao, ganache de chocolate negro 70% y decoración en rosetones.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg",
            dificultad = Dificultad.DIFICIL,
            porciones = 8,
            calificacionPromedio = 4.7f,
            pasos = listOf("Mezclar ingredientes secos", "Agregar húmedos", "Hornear", "Preparar ganache", "Decorar"),
            ingredientes = listOf(
                Ingrediente(13, "Cacao", 80f, UnidadMedida.GRAMOS),
                Ingrediente(14, "Chocolate negro", 200f, UnidadMedida.GRAMOS),
                Ingrediente(15, "Harina", 250f, UnidadMedida.GRAMOS),
                Ingrediente(16, "Azúcar", 200f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(4, "Torta de Chocolate", "wRF9RCuAuMo", 1200, "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg")),
            categoria = catPostre
        ),
        Receta(
            id = 5,
            nombre = "Tacos al Pastor",
            descripcion = "Cerdo marinado en achiote y chiles, asado al carbón, con piña, cilantro y cebolla.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/OnkIw01699270432.jpg",
            dificultad = Dificultad.MEDIO,
            porciones = 6,
            calificacionPromedio = 4.8f,
            pasos = listOf("Marinar cerdo", "Asar al carbón", "Preparar piña", "Armar tacos"),
            ingredientes = listOf(
                Ingrediente(17, "Cerdo", 500f, UnidadMedida.GRAMOS),
                Ingrediente(18, "Achiote", 30f, UnidadMedida.GRAMOS),
                Ingrediente(19, "Piña", 150f, UnidadMedida.GRAMOS),
                Ingrediente(20, "Tortillas", 12f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(5, "Tacos al Pastor", "orr3UEMeH8c", 800, "https://www.themealdb.com/images/media/meals/OnkIw01699270432.jpg")),
            categoria = catMexicana
        ),
        Receta(
            id = 6,
            nombre = "Sushi Rolls",
            descripcion = "Arroz de sushi avinagrado, nori, salmón fresco y aguacate. Acompañado de wasabi y jengibre.",
            imagenUrl = "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg",
            dificultad = Dificultad.DIFICIL,
            porciones = 4,
            calificacionPromedio = 4.5f,
            pasos = listOf("Preparar arroz", "Extender nori", "Colocar relleno", "Enrollar", "Cortar"),
            ingredientes = listOf(
                Ingrediente(21, "Arroz sushi", 300f, UnidadMedida.GRAMOS),
                Ingrediente(22, "Salmón", 200f, UnidadMedida.GRAMOS),
                Ingrediente(23, "Aguacate", 100f, UnidadMedida.GRAMOS),
                Ingrediente(24, "Nori", 5f, UnidadMedida.GRAMOS)
            ),
            videos = listOf(Video(6, "Sushi Rolls", "I3_QTry5FOs", 1500, "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg")),
            categoria = catJaponesa
        )
    )
}
