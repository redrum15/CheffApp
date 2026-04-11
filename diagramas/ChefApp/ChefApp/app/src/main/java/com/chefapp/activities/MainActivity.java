package com.chefapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chefapp.R;
import com.chefapp.fragments.BotonesFragment;
import com.chefapp.fragments.FotosFragment;
import com.chefapp.fragments.PerfilFragment;
import com.chefapp.fragments.VideoFragment;
import com.chefapp.fragments.WebFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navPerfil, navFotos, navVideo, navWeb, navBotones;
    private int currentNav = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navPerfil  = findViewById(R.id.nav_perfil);
        navFotos   = findViewById(R.id.nav_fotos);
        navVideo   = findViewById(R.id.nav_video);
        navWeb     = findViewById(R.id.nav_web);
        navBotones = findViewById(R.id.nav_botones);

        navPerfil.setOnClickListener(v  -> selectNav(0));
        navFotos.setOnClickListener(v   -> selectNav(1));
        navVideo.setOnClickListener(v   -> selectNav(2));
        navWeb.setOnClickListener(v     -> selectNav(3));
        navBotones.setOnClickListener(v -> selectNav(4));

        // Default: Perfil
        selectNav(0);
    }

    private void selectNav(int index) {
        if (currentNav == index) return;
        currentNav = index;

        updateNavUI();
        loadFragment(index);
    }

    private void updateNavUI() {
        LinearLayout[] navItems = {navPerfil, navFotos, navVideo, navWeb, navBotones};

        for (int i = 0; i < navItems.length; i++) {
            LinearLayout item = navItems[i];
            boolean active = (i == currentNav);

            item.setBackgroundResource(active ? R.drawable.bg_sidebar_active : 0);

            // Tint all children
            for (int j = 0; j < item.getChildCount(); j++) {
                View child = item.getChildAt(j);
                int colorRes = active ? R.color.text_white : R.color.sidebar_icon;
                int color = getResources().getColor(colorRes, null);

                if (child instanceof ImageView) {
                    ((ImageView) child).setColorFilter(color);
                } else if (child instanceof android.widget.TextView) {
                    ((android.widget.TextView) child).setTextColor(color);
                }
            }
        }
    }

    private void loadFragment(int index) {
        Fragment fragment;
        switch (index) {
            case 1:  fragment = new FotosFragment();   break;
            case 2:  fragment = new VideoFragment();   break;
            case 3:  fragment = new WebFragment();     break;
            case 4:  fragment = new BotonesFragment(); break;
            default: fragment = new PerfilFragment();  break;
        }

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        tx.replace(R.id.fragment_container, fragment);
        tx.commit();
    }
}
