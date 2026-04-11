package com.chefapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chefapp.R;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_NAME    = "recipe_name";
    public static final String EXTRA_RECIPE_INFO    = "recipe_info";
    public static final String EXTRA_RECIPE_DESC    = "recipe_desc";
    public static final String EXTRA_RECIPE_RATING  = "recipe_rating";
    public static final String EXTRA_RECIPE_IMAGE   = "recipe_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        String name   = getIntent().getStringExtra(EXTRA_RECIPE_NAME);
        String info   = getIntent().getStringExtra(EXTRA_RECIPE_INFO);
        String desc   = getIntent().getStringExtra(EXTRA_RECIPE_DESC);
        float  rating = getIntent().getFloatExtra(EXTRA_RECIPE_RATING, 0f);
        int    imgRes = getIntent().getIntExtra(EXTRA_RECIPE_IMAGE, R.drawable.food_pasta);

        if (name != null)  ((TextView)  findViewById(R.id.tv_detail_name)).setText(name);
        if (info != null)  ((TextView)  findViewById(R.id.tv_detail_info)).setText(info);
        if (desc != null)  ((TextView)  findViewById(R.id.tv_detail_desc)).setText(desc);
        ((TextView) findViewById(R.id.tv_detail_rating)).setText(String.valueOf(rating));
        ((ImageView) findViewById(R.id.img_detail)).setImageResource(imgRes);

        // Back button
        findViewById(R.id.btn_detail_back).setOnClickListener(v -> finish());
    }
}
