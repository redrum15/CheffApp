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

    private val navegador = NavegadorWeb()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarWebView()

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

        val accesos = navegador.accesosRapidos
        binding.itemAcceso1.setOnClickListener { cargarUrl(accesos[0].second) }
        binding.itemAcceso2.setOnClickListener { cargarUrl(accesos[1].second) }
        binding.itemAcceso3.setOnClickListener { cargarUrl(accesos[2].second) }
        binding.itemAcceso4.setOnClickListener { cargarUrl(accesos[3].second) }

        binding.tvAcceso1.text = accesos[0].first
        binding.tvAcceso2.text = accesos[1].first
        binding.tvAcceso3.text = accesos[2].first
        binding.tvAcceso4.text = accesos[3].first

        cargarUrl(navegador.urlActual)
    }

    private fun configurarWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

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

    private fun mostrarHistorial() {
        actualizarHistorialUI()
    }

    private fun cargarUrl(url: String) {
        navegador.cargarPagina(url)
        binding.etUrl.setText(navegador.urlActual)
        binding.webView.loadUrl(navegador.urlActual)
        actualizarHistorialUI()
    }

    fun agregarAccesoRapido(nombre: String, url: String) {
        navegador.agregarAccesoRapido(nombre, url)
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