package com.chefapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chefapp.R;
import com.chefapp.adapters.RecipeAdapter;
import com.chefapp.models.DataProvider;
import com.chefapp.models.Recipe;

import java.util.List;

public class FotosFragment extends Fragment {

    private RecipeAdapter adapter;
    private List<Recipe> recipes;
    private LinearLayout featuredCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fotos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipes = DataProvider.getSampleRecipes();

        TextView tvCount = view.findViewById(R.id.tv_recipe_count);
        tvCount.setText(recipes.size() + " recetas");

        RecyclerView rv = view.findViewById(R.id.rv_recipes);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeAdapter(recipes);
        rv.setAdapter(adapter);

        featuredCard = view.findViewById(R.id.featured_card);

        adapter.setOnRecipeClickListener((recipe, position) -> showFeatured(view, recipe));
    }

    private void showFeatured(View view, Recipe recipe) {
        featuredCard.setVisibility(View.VISIBLE);

        ImageView img = view.findViewById(R.id.img_featured);
        img.setImageResource(recipe.getImageResId());

        ((TextView) view.findViewById(R.id.tv_featured_title)).setText(recipe.getName());
        ((TextView) view.findViewById(R.id.tv_featured_cuisine)).setText(recipe.getCuisine());
        ((TextView) view.findViewById(R.id.tv_featured_time)).setText(recipe.getTimeMinutes() + " min");
        ((TextView) view.findViewById(R.id.tv_featured_diff)).setText(recipe.getDifficulty());
        ((TextView) view.findViewById(R.id.tv_featured_rating)).setText(String.valueOf(recipe.getRating()));
        ((TextView) view.findViewById(R.id.tv_featured_desc)).setText(recipe.getDescription());

        featuredCard.setOnClickListener(v -> featuredCard.setVisibility(View.GONE));
    }
}
