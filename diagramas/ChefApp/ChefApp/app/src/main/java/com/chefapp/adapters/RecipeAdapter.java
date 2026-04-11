package com.chefapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chefapp.R;
import com.chefapp.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe, int position);
    }

    private final List<Recipe> recipes;
    private OnRecipeClickListener listener;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvName.setText(recipe.getName());
        holder.tvInfo.setText(recipe.getInfoLine());
        holder.tvRating.setText(String.valueOf(recipe.getRating()));
        holder.imgRecipe.setImageResource(recipe.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onRecipeClick(recipe, position);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipe;
        TextView tvName, tvInfo, tvRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipe = itemView.findViewById(R.id.img_recipe);
            tvName = itemView.findViewById(R.id.tv_recipe_name);
            tvInfo = itemView.findViewById(R.id.tv_recipe_info);
            tvRating = itemView.findViewById(R.id.tv_recipe_rating);
        }
    }
}
