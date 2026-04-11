package com.chefapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
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

public class VideoFragment extends Fragment {

    private boolean isPlaying = false;
    private ImageView ivPlayIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Play/Pause button
        ivPlayIcon = view.findViewById(R.id.iv_play_icon);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(v -> togglePlay());

        // Prev/Next
        view.findViewById(R.id.btn_prev).setOnClickListener(v -> {
            // previous track logic
        });
        view.findViewById(R.id.btn_next).setOnClickListener(v -> {
            // next track logic
        });

        // Volume SeekBar
        SeekBar volumeBar = view.findViewById(R.id.seekbar_volume);
        TextView tvVolume = view.findViewById(R.id.tv_volume);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvVolume.setText(String.valueOf(progress));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // More Recipes RecyclerView
        List<Recipe> recipes = DataProvider.getSampleRecipes();
        RecyclerView rv = view.findViewById(R.id.rv_more_recipes);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeAdapter adapter = new RecipeAdapter(recipes);
        rv.setAdapter(adapter);
        adapter.setOnRecipeClickListener((recipe, position) -> loadRecipe(view, recipe));
    }

    private void togglePlay() {
        isPlaying = !isPlaying;
        ivPlayIcon.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    private void loadRecipe(View view, Recipe recipe) {
        ((TextView) view.findViewById(R.id.tv_video_recipe_name)).setText(recipe.getName());
        if (isPlaying) togglePlay();
    }
}
