package com.chefapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chefapp.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class PerfilFragment extends Fragment {

    private LinearLayout btnBajarPeso, btnMantener, btnSubirPeso;
    private ChipGroup chipAlergias, chipFavoritos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Info rows
        setupInfoRow(view, R.id.row_edad, "Edad", "28 años");
        setupInfoRow(view, R.id.row_peso, "Peso actual", "72 kg");
        setupInfoRow(view, R.id.row_estatura, "Estatura", "1.75 m");

        // Edit button
        view.findViewById(R.id.btn_edit).setOnClickListener(v ->
                Toast.makeText(getContext(), "Editar perfil", Toast.LENGTH_SHORT).show());

        // Objective buttons
        btnBajarPeso = view.findViewById(R.id.btn_bajar_peso);
        btnMantener = view.findViewById(R.id.btn_mantener);
        btnSubirPeso = view.findViewById(R.id.btn_subir_peso);

        btnBajarPeso.setOnClickListener(v -> selectObjective(0));
        btnMantener.setOnClickListener(v -> selectObjective(1));
        btnSubirPeso.setOnClickListener(v -> selectObjective(2));

        // Alergias chips
        chipAlergias = view.findViewById(R.id.chip_group_alergias);
        addChip(chipAlergias, "Mariscos 🦐", true);
        addChip(chipAlergias, "Lácteos 🥛", true);
        addChip(chipAlergias, "Frutos secos 🥜", true);
        addAddChip(chipAlergias);

        // Favoritos chips
        chipFavoritos = view.findViewById(R.id.chip_group_favoritos);
        addChip(chipFavoritos, "Pasta 🍝", false);
        addChip(chipFavoritos, "Pizza 🍕", false);
        addChip(chipFavoritos, "Sushi 🍣", false);
        addChip(chipFavoritos, "Tacos 🌮", false);
        addAddChip(chipFavoritos);
    }

    private void setupInfoRow(View parent, int rowId, String label, String value) {
        View row = parent.findViewById(rowId);
        if (row != null) {
            TextView tvLabel = row.findViewById(R.id.tv_label);
            TextView tvValue = row.findViewById(R.id.tv_value);
            if (tvLabel != null) tvLabel.setText(label);
            if (tvValue != null) tvValue.setText(value);
        }
    }

    private void selectObjective(int index) {
        int activeBackground = R.drawable.bg_objective_selected;
        int normalBackground = R.drawable.bg_objective_normal;
        int activeColor = R.color.primary_orange;
        int normalColor = R.color.text_primary;

        btnBajarPeso.setBackgroundResource(index == 0 ? activeBackground : normalBackground);
        btnMantener.setBackgroundResource(index == 1 ? activeBackground : normalBackground);
        btnSubirPeso.setBackgroundResource(index == 2 ? activeBackground : normalBackground);

        setObjectiveTextColor(btnBajarPeso, index == 0 ? activeColor : normalColor);
        setObjectiveTextColor(btnMantener, index == 1 ? activeColor : normalColor);
        setObjectiveTextColor(btnSubirPeso, index == 2 ? activeColor : normalColor);
    }

    private void setObjectiveTextColor(LinearLayout container, int colorRes) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextColor(getResources().getColor(colorRes, null));
            }
        }
    }

    private void addChip(ChipGroup group, String text, boolean isAllergy) {
        Chip chip = new Chip(requireContext());
        chip.setText(text);
        chip.setChipBackgroundColorResource(isAllergy ? R.color.allergy_bg : R.color.tag_bg);
        chip.setChipStrokeColorResource(isAllergy ? R.color.allergy_border : R.color.primary_orange);
        chip.setChipStrokeWidth(2f);
        chip.setTextColor(getResources().getColor(R.color.primary_orange, null));
        chip.setTextSize(13f);
        chip.setCloseIconVisible(true);
        chip.setCloseIconTint(android.content.res.ColorStateList.valueOf(
                getResources().getColor(R.color.primary_orange, null)));
        chip.setOnCloseIconClickListener(v -> group.removeView(chip));
        group.addView(chip);
    }

    private void addAddChip(ChipGroup group) {
        Chip chip = new Chip(requireContext());
        chip.setText("+ Agregar");
        chip.setChipBackgroundColorResource(R.color.bg_white);
        chip.setChipStrokeColorResource(R.color.divider);
        chip.setChipStrokeWidth(2f);
        chip.setTextColor(getResources().getColor(R.color.text_secondary, null));
        chip.setTextSize(13f);
        chip.setOnClickListener(v ->
                Toast.makeText(getContext(), "Agregar alergia", Toast.LENGTH_SHORT).show());
        group.addView(chip);
    }
}
