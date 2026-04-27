# 🍳 ChefApp

Aplicación Android de recetas y cocina desarrollada en Kotlin. Permite explorar recetas, ver tutoriales en video, gestionar preferencias dietéticas y navegar sitios culinarios desde un navegador integrado.

---

## Características principales

- **Perfil de usuario** — Datos personales, cálculo de IMC, objetivo nutricional, alergias y preferencias dietéticas (vegetariano, vegano, keto, sin gluten, sin lactosa).
- **Galería de recetas** — Catálogo con 6 recetas de distintas categorías (italiana, mexicana, japonesa, postres, ensaladas). Filtrado por alergias y restricciones alimentarias.
- **Tutoriales en video** — Reproducción de videos de YouTube embebidos con controles de volumen y navegación entre videos.
- **Navegador web** — Navegador integrado con historial, barra de URL y accesos rápidos a sitios como RecetasGratis, Tasty, TasteAtlas y Epicurious.
- **Ajuste de recetas** — Escalado de porciones (1–10), conversión de unidades (gramos ↔ onzas) y sistema de calificación (1–5 estrellas).
- **Vista de botones** — Vista con diferentes botones disponibles
---

## Tecnologías

| Categoría | Tecnología | Versión |
|-----------|-----------|---------|
| Lenguaje | Kotlin | 2.2.10 |
| Build | Gradle | 9.1.0 |
| SDK objetivo | compileSdk 35 | Android 15 |
| SDK mínimo | minSdk 29 | Android 10 |
| UI | Material Design | 1.10.0 |
| Layouts | ConstraintLayout | 2.1.4 |
| Imágenes | Glide | 4.16.0 |
| Listas | RecyclerView | 1.3.2 |
| Navegación | DrawerLayout | 1.2.0 |
| View Binding | Nativo | — |

---

## Estructura del proyecto

```
app/src/main/java/com/example/chefapp/
├── activities/
│   ├── SplashActivity      # Pantalla de carga animada
│   ├── LoginActivity        # Autenticación de usuario
│   └── MainActivity         # Contenedor principal con Navigation Drawer
├── fragments/
│   ├── PerfilFragment       # Perfil, IMC y preferencias
│   ├── FotosFragment        # Galería de recetas
│   ├── VideoFragment        # Tutoriales en video
│   ├── WebFragment          # Navegador web integrado
│   ├── BotonesFragment      # Ajustes de porciones y calificación
│   └── LeftFragment         # Menú lateral de navegación
├── models/
│   ├── Usuario              # Datos del usuario y cálculo de IMC
│   ├── Receta               # Receta con categoría y dificultad
│   ├── Ingrediente          # Ingrediente con conversión de unidades
│   ├── Video                # Video de YouTube embebido
│   ├── Categoria            # Categoría de receta
│   ├── Alergia              # Alergia alimentaria
│   └── ...                  # Enums (Dificultad, TipoDieta, UnidadMedida, etc.)
├── helpers/
│   ├── GaleriaRecetas       # Navegación y filtrado de recetas
│   ├── NavegadorWeb         # Historial y accesos rápidos del navegador
│   ├── ReproductorVideo     # Estado de reproducción de video
│   └── SistemaRecomendacion # Motor de recomendación de recetas
└── adapters/                # Adaptadores para RecyclerView
```

---

## Cómo ejecutar

1. Abrí el proyecto en Android Studio.
2. Sincronizá Gradle.
3. Ejecutá en un emulador o dispositivo con Android 10+.

**Credenciales de prueba:**
- Email: `jorge.martinez@email.com`
- Contraseña: `1234`

---

## Permisos

- `INTERNET` — Requerido para el navegador web y carga de imágenes.
