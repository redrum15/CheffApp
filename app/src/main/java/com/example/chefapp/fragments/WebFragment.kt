package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.chefapp.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    // Variable URL actual
    private var urlActual = "https://www.recetasgratis.net"

    // Accesos rapidos
    private val accesosRapidos = listOf(
        Pair("🥘 RecetasGratis", "https://www.recetasgratis.net"),
        Pair("📺 Tasty",         "https://tasty.co"),
        Pair("⭐ TasteAtlas",    "https://www.tasteatlas.com"),
        Pair("🍳 Epicurious",    "https://www.epicurious.com")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar WebView
        configurarWebView()

        // Vincular eventos de botones de navegacion
        binding.btnIr.setOnClickListener {
            val url = binding.etUrl.text.toString()
            if (url.isNotEmpty()) cargarUrl(url)
        }

        binding.btnAtras.setOnClickListener {
            if (binding.webView.canGoBack()) binding.webView.goBack()
        }

        binding.btnAdelante.setOnClickListener {
            if (binding.webView.canGoForward()) binding.webView.goForward()
        }

        binding.btnRecargar.setOnClickListener {
            binding.webView.reload()
        }

        // Accesos rapidos — evento clic en cada item
        binding.itemAcceso1.setOnClickListener { cargarUrl(accesosRapidos[0].second) }
        binding.itemAcceso2.setOnClickListener { cargarUrl(accesosRapidos[1].second) }
        binding.itemAcceso3.setOnClickListener { cargarUrl(accesosRapidos[2].second) }
        binding.itemAcceso4.setOnClickListener { cargarUrl(accesosRapidos[3].second) }

        // Textos de accesos rapidos
        binding.tvAcceso1.text = accesosRapidos[0].first
        binding.tvAcceso2.text = accesosRapidos[1].first
        binding.tvAcceso3.text = accesosRapidos[2].first
        binding.tvAcceso4.text = accesosRapidos[3].first

        // Cargar URL inicial
        cargarUrl(urlActual)
    }

    // Configura el WebView con JavaScript habilitado
    private fun configurarWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            // Detecta inicio y fin de carga
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    binding.tvEstado.text = "● Cargando"
                    binding.progressWebView.visibility = View.VISIBLE
                    binding.etUrl.setText(url)
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.tvEstado.text = "✓ Listo"
                    binding.progressWebView.visibility = View.GONE
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progressWebView.progress = newProgress
                }
            }
        }
    }

    // Carga una URL y guarda en historial
    private fun cargarUrl(url: String) {
        val urlFinal = if (!url.startsWith("http")) "https://$url" else url
        urlActual = urlFinal
        binding.etUrl.setText(urlFinal)
        binding.webView.loadUrl(urlFinal)
        agregarAlHistorial(urlFinal)
    }

    // Agrega URL al historial visible (maximo 5)
    private val historial = mutableListOf<String>()

    private fun agregarAlHistorial(url: String) {
        historial.remove(url)
        historial.add(0, url)
        if (historial.size > 5) historial.removeAt(historial.size - 1)
        actualizarHistorialUI()
    }

    private fun actualizarHistorialUI() {
        val tvs = listOf(
            binding.tvHistorial1, binding.tvHistorial2,
            binding.tvHistorial3, binding.tvHistorial4, binding.tvHistorial5
        )
        val rows = listOf(
            binding.rowHistorial1, binding.rowHistorial2,
            binding.rowHistorial3, binding.rowHistorial4, binding.rowHistorial5
        )
        tvs.forEachIndexed { i, tv ->
            if (i < historial.size) {
                rows[i].visibility = View.VISIBLE
                tv.text = "🕓 ${historial[i]}"
                rows[i].setOnClickListener { cargarUrl(historial[i]) }
            } else {
                rows[i].visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}