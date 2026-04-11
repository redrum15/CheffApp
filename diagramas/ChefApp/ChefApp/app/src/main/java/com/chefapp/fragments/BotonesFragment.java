package com.chefapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chefapp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BotonesFragment extends Fragment {

    private int currentRating = 4;
    private int currentDifficulty = 1; // 0=Fácil, 1=Medio, 2=Difícil
    private ImageView[] stars;
    private TextView btnFacil, btnMedio, btnDificil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_botones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRating(view);
        setupUnits(view);
        setupDifficulty(view);
        setupPorciones(view);
        setupRestricciones(view);
    }

    private void setupRating(View view) {
        stars = new ImageView[]{
                view.findViewById(R.id.star1),
                view.findViewById(R.id.star2),
                view.findViewById(R.id.star3),
                view.findViewById(R.id.star4),
                view.findViewById(R.id.star5)
        };
        TextView tvLabel = view.findViewById(R.id.tv_rating_label);

        for (int i = 0; i < stars.length; i++) {
            final int rating = i + 1;
            stars[i].setOnClickListener(v -> {
                currentRating = rating;
                updateStars(tvLabel);
            });
        }
        updateStars(tvLabel);
    }

    private void updateStars(TextView tvLabel) {
        String[] labels = {"", "Regular", "Bueno", "Muy bueno", "Muy bueno", "Excelente"};
        for (int i = 0; i < stars.length; i++) {
            stars[i].setImageResource(i < currentRating ?
                    R.drawable.ic_star_filled : R.drawable.ic_star_outline);
        }
        if (currentRating >= 0 && currentRating <= 5) {
            tvLabel.setText(labels[currentRating]);
        }
    }

    private void setupUnits(View view) {
        SwitchMaterial switchUnits = view.findViewById(R.id.switch_units);
        switchUnits.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(getContext(),
                        isChecked ? "Onzas / °F" : "Gramos / °C",
                        Toast.LENGTH_SHORT).show());
    }

    private void setupDifficulty(View view) {
        btnFacil = view.findViewById(R.id.btn_facil);
        btnMedio = view.findViewById(R.id.btn_medio);
        btnDificil = view.findViewById(R.id.btn_dificil);

        btnFacil.setOnClickListener(v -> selectDifficulty(0));
        btnMedio.setOnClickListener(v -> selectDifficulty(1));
        btnDificil.setOnClickListener(v -> selectDifficulty(2));

        selectDifficulty(1); // Default: Medio
    }

    private void selectDifficulty(int index) {
        currentDifficulty = index;
        btnFacil.setBackgroundResource(index == 0 ? R.drawable.bg_difficulty_selected : R.drawable.bg_difficulty_normal);
        btnMedio.setBackgroundResource(index == 1 ? R.drawable.bg_difficulty_selected : R.drawable.bg_difficulty_normal);
        btnDificil.setBackgroundResource(index == 2 ? R.drawable.bg_difficulty_selected : R.drawable.bg_difficulty_normal);

        btnFacil.setTextColor(getResources().getColor(index == 0 ? R.color.text_white : R.color.text_primary, null));
        btnMedio.setTextColor(getResources().getColor(index == 1 ? R.color.text_white : R.color.text_primary, null));
        btnDificil.setTextColor(getResources().getColor(index == 2 ? R.color.text_white : R.color.text_primary, null));
    }

    private void setupPorciones(View view) {
        SeekBar seekBar = view.findViewById(R.id.seekbar_porciones);
        TextView tvValue = view.findViewById(R.id.tv_porciones_value);
        TextView tvTiempo = view.findViewById(R.id.tv_tiempo_estimado);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int porciones = progress + 1;
                tvValue.setText(String.valueOf(porciones));
                int baseTime = 35;
                int estimatedTime = baseTime + (porciones - 1) * 5;
                tvTiempo.setText("Tiempo estimado: " + estimatedTime + " min");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        view.findViewById(R.id.btn_calcular).setOnClickListener(v -> {
            int porciones = seekBar.getProgress() + 1;
            String diff = currentDifficulty == 0 ? "Fácil" : currentDifficulty == 1 ? "Medio" : "Difícil";
            Toast.makeText(getContext(),
                    "Calculando para " + porciones + " porciones (" + diff + ")",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRestricciones(View view) {
        CheckBox cbGluten = view.findViewById(R.id.cb_sin_gluten);
        CheckBox cbVegano = view.findViewById(R.id.cb_vegano);
        CheckBox cbLactosa = view.findViewById(R.id.cb_sin_lactosa);
        CheckBox cbMariscos = view.findViewById(R.id.cb_sin_mariscos);

        View.OnClickListener listener = v -> {
            StringBuilder sb = new StringBuilder("Filtros: ");
            if (cbGluten.isChecked()) sb.append("Sin gluten ");
            if (cbVegano.isChecked()) sb.append("Vegano ");
            if (cbLactosa.isChecked()) sb.append("Sin lactosa ");
            if (cbMariscos.isChecked()) sb.append("Sin mariscos ");
            Toast.makeText(getContext(), sb.toString().trim(), Toast.LENGTH_SHORT).show();
        };

        cbGluten.setOnClickListener(listener);
        cbVegano.setOnClickListener(listener);
        cbLactosa.setOnClickListener(listener);
        cbMariscos.setOnClickListener(listener);
    }
}
