package com.example.chefapp.models

data class Recipe(
    val id: Int,
    val title: String,
    val category: String,
    val time: String,
    val difficulty: String,
    val rating: Double,
    val imageUrl: String,
    val ytId: String,
    val description: String
)

// Datos de las 6 recetas — fuente unica de verdad para toda la app
object RecipeData {
    val recipes = listOf(
        Recipe(
            id = 1,
            title = "Pasta Carbonara",
            category = "Italiana",
            time = "25 min",
            difficulty = "Medio",
            rating = 4.8,
            imageUrl = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",
            ytId = "D_2DBLAt57c",
            description = "Clásico romano con guanciale, pecorino, huevo y pimienta negra. Sin crema — la tradición manda."
        ),
        Recipe(
            id = 2,
            title = "Ensalada César",
            category = "Ensalada",
            time = "15 min",
            difficulty = "Fácil",
            rating = 4.6,
            imageUrl = "https://www.themealdb.com/images/media/meals/qq10wb1585908761.jpg",
            ytId = "UriKNqnrfMc",
            description = "Lechuga romana, crutones crujientes, parmesano y aderezo César casero con anchoas."
        ),
        Recipe(
            id = 3,
            title = "Pizza Margarita",
            category = "Italiana",
            time = "35 min",
            difficulty = "Medio",
            rating = 4.9,
            imageUrl = "https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg",
            ytId = "1-SJGQ2HBt8",
            description = "Masa artesanal, salsa de tomate San Marzano, mozzarella fresca y albahaca."
        ),
        Recipe(
            id = 4,
            title = "Torta de Chocolate",
            category = "Postre",
            time = "50 min",
            difficulty = "Difícil",
            rating = 4.7,
            imageUrl = "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg",
            ytId = "wRF9RCuAuMo",
            description = "Bizcocho húmedo de cacao, ganache de chocolate negro 70% y decoración en rosetones."
        ),
        Recipe(
            id = 5,
            title = "Tacos al Pastor",
            category = "Mexicana",
            time = "40 min",
            difficulty = "Medio",
            rating = 4.8,
            imageUrl = "https://www.themealdb.com/images/media/meals/OnkIw01699270432.jpg",
            ytId = "orr3UEMeH8c",
            description = "Cerdo marinado en achiote y chiles, asado al carbón, con piña, cilantro y cebolla."
        ),
        Recipe(
            id = 6,
            title = "Sushi Rolls",
            category = "Japonesa",
            time = "60 min",
            difficulty = "Difícil",
            rating = 4.5,
            imageUrl = "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg",
            ytId = "I3_QTry5FOs",
            description = "Arroz de sushi avinagrado, nori, salmón fresco y aguacate. Acompañado de wasabi y jengibre."
        )
    )
}