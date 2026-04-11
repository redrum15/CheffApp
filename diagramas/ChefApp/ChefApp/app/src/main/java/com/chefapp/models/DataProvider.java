package com.chefapp.models;

import com.chefapp.R;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<Recipe> getSampleRecipes() {
        List<Recipe> list = new ArrayList<>();
        list.add(new Recipe(
                "Pasta Carbonara", "Italiana", 25, "Medio", 4.8f,
                "Clásica pasta italiana con guanciale, huevo, queso pecorino y pimienta negra. Cremosa y auténtica (sin nata). Tip: mezcla el huevo fuera del fuego para evitar que se corte.",
                R.drawable.food_pasta));
        list.add(new Recipe(
                "Ensalada César", "Ensalada", 15, "Fácil", 4.6f,
                "Ensalada fresca con lechuga romana, crutones crujientes, queso parmesano y aderezo César casero. Perfecta como entrada o plato principal ligero.",
                R.drawable.food_salad));
        list.add(new Recipe(
                "Pizza Margarita", "Italiana", 35, "Medio", 4.5f,
                "Pizza napolitana con masa crujiente, tomate San Marzano, mozzarella fresca y albahaca. Horneada a alta temperatura para un resultado perfecto.",
                R.drawable.food_pizza));
        list.add(new Recipe(
                "Tacos al Pastor", "Mexicana", 40, "Medio", 4.7f,
                "Tacos con carne de cerdo marinada en chile, piña, cebolla y cilantro. Receta auténtica con achiote y chiles secos.",
                R.drawable.food_pasta));
        list.add(new Recipe(
                "Sushi Variado", "Japonesa", 60, "Difícil", 4.9f,
                "Surtido de nigiri y maki con salmón, atún y camarón. Arroz de sushi perfectamente sazonado con vinagre de arroz.",
                R.drawable.food_salad));
        list.add(new Recipe(
                "Paella Valenciana", "Española", 50, "Difícil", 4.8f,
                "Paella tradicional con pollo, conejo, judías verdes y garrofón. Arroz bomba cocinado con caldo casero y azafrán.",
                R.drawable.food_pizza));
        return list;
    }

    public static List<QuickAccessSite> getQuickAccessSites() {
        List<QuickAccessSite> sites = new ArrayList<>();
        sites.add(new QuickAccessSite("RecetasGratis", "https://www.recetasgratis.net", true));
        sites.add(new QuickAccessSite("Tasty", "https://tasty.co", true));
        sites.add(new QuickAccessSite("AllRecipes", "https://www.allrecipes.com", false));
        return sites;
    }
}
