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
import com.example.chefapp.helpers.NavegadorWeb

class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    // Navegador web del diagrama UML
    private val navegador = NavegadorWeb()

    // Accesos rapidos con etiquetas para UI
    private val accesosRapidosUI = listOf(
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
            navegador.atras()
            if (binding.webView.canGoBack()) binding.webView.goBack()
        }

        binding.btnAdelante.setOnClickListener {
            navegador.adelante()
            if (binding.webView.canGoForward()) binding.webView.goForward()
        }

        binding.btnRecargar.setOnClickListener {
            navegador.recargar()
            binding.webView.reload()
        }

        // Accesos rapidos — evento clic en cada item
        binding.itemAcceso1.setOnClickListener { cargarUrl(accesosRapidosUI[0].second) }
        binding.itemAcceso2.setOnClickListener { cargarUrl(accesosRapidosUI[1].second) }
        binding.itemAcceso3.setOnClickListener { cargarUrl(accesosRapidosUI[2].second) }
        binding.itemAcceso4.setOnClickListener { cargarUrl(accesosRapidosUI[3].second) }

        // Textos de accesos rapidos
        binding.tvAcceso1.text = accesosRapidosUI[0].first
        binding.tvAcceso2.text = accesosRapidosUI[1].first
        binding.tvAcceso3.text = accesosRapidosUI[2].first
        binding.tvAcceso4.text = accesosRapidosUI[3].first

        // Cargar URL inicial
        cargarUrl(navegador.urlActual)
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

    // Muestra el historial de navegación
    private fun mostrarHistorial() {
        actualizarHistorialUI()
    }

    // Carga una URL y guarda en historial via NavegadorWeb
    private fun cargarUrl(url: String) {
        navegador.cargarPagina(url)
        binding.etUrl.setText(navegador.urlActual)
        binding.webView.loadUrl(navegador.urlActual)
        actualizarHistorialUI()
    }

    // Agrega acceso rápido
    fun agregarAccesoRapido(url: String) {
        navegador.agregarAccesoRapido(url)
    }

    private fun actualizarHistorialUI() {
        val historial = navegador.mostrarHistorial()
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