package com.chefapp.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chefapp.R;
import com.chefapp.adapters.QuickAccessAdapter;
import com.chefapp.models.DataProvider;

public class WebFragment extends Fragment {

    private WebView webView;
    private EditText etUrl;
    private TextView tvStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.webview);
        etUrl = view.findViewById(R.id.et_url);
        tvStatus = view.findViewById(R.id.tv_status);

        // Configure WebView
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                tvStatus.setText("✓ Listo");
                tvStatus.setTextColor(getResources().getColor(R.color.imc_normal, null));
                etUrl.setText(url);
            }
            @Override public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                tvStatus.setText("⟳ Cargando...");
                tvStatus.setTextColor(getResources().getColor(R.color.text_secondary, null));
            }
        });

        webView.setWebChromeClient(new WebChromeClient());

        // Go button
        view.findViewById(R.id.btn_go).setOnClickListener(v -> loadUrl());

        // URL field Enter
        etUrl.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                loadUrl();
                return true;
            }
            return false;
        });

        // Nav buttons
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            if (webView.canGoBack()) webView.goBack();
        });
        view.findViewById(R.id.btn_forward).setOnClickListener(v -> {
            if (webView.canGoForward()) webView.goForward();
        });
        view.findViewById(R.id.btn_reload).setOnClickListener(v -> webView.reload());

        // Quick Access RecyclerView
        RecyclerView rvQuick = view.findViewById(R.id.rv_quick_access);
        rvQuick.setLayoutManager(new LinearLayoutManager(getContext()));
        QuickAccessAdapter adapter = new QuickAccessAdapter(DataProvider.getQuickAccessSites());
        rvQuick.setAdapter(adapter);
        adapter.setOnSiteClickListener(site -> {
            etUrl.setText(site.getUrl());
            loadUrlString(site.getUrl());
        });

        // Load default URL
        loadUrlString("https://www.recetasgratis.net");
    }

    private void loadUrl() {
        String url = etUrl.getText().toString().trim();
        if (!url.isEmpty()) loadUrlString(url);
    }

    private void loadUrlString(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        webView.loadUrl(url);
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroyView();
    }
}
