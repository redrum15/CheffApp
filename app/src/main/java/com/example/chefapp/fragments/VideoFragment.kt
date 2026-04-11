package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chefapp.databinding.FragmentVideoBinding
import com.example.chefapp.databinding.FragmentVideoListItemBinding
import com.example.chefapp.helpers.ReproductorVideo
import com.example.chefapp.models.Dificultad
import com.example.chefapp.models.Receta
import com.example.chefapp.models.RecetaData

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val reproductor = ReproductorVideo()

    private var indiceActivo = 0
    private var estaMuteado  = false
    private val recetas      = RecetaData.recetas
    private lateinit var videoAdapter: VideoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reproductor.cargarDesdeRecetas(recetas)

        configurarWebView()
        configurarLista()

        binding.btnAnterior.setOnClickListener {
            if (indiceActivo > 0) {
                indiceActivo--
                reproductor.anterior()
                cargarReceta(indiceActivo)
            }
        }
        binding.btnPlayPause.setOnClickListener {
            reproductor.reproducir()
        }
        binding.btnSiguiente.setOnClickListener {
            if (indiceActivo < recetas.size - 1) {
                indiceActivo++
                reproductor.siguiente()
                cargarReceta(indiceActivo)
            }
        }
        binding.btnMute.setOnClickListener {
            estaMuteado = !estaMuteado
            binding.btnMute.text = if (estaMuteado) "🔇" else if (reproductor.volumen < 50) "🔈" else "🔊"
        }

        binding.seekBarVolumen.progress = reproductor.volumen
        binding.seekBarVolumen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                reproductor.ajustarVolumen(progress)
                binding.tvVolumen.text = "${reproductor.volumen}"
                binding.btnMute.text = when {
                    reproductor.volumen == 0 -> "🔇"
                    reproductor.volumen < 50 -> "🔈"
                    else -> "🔊"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        cargarReceta(0)
    }

    private fun configurarWebView() {
        binding.webViewVideo.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            webViewClient = WebViewClient()
        }
    }

    private fun configurarLista() {
        videoAdapter = VideoListAdapter(recetas) { indice ->
            indiceActivo = indice
            val video = recetas[indice].videos.firstOrNull()
            if (video != null) reproductor.seleccionarVideo(video)
            cargarReceta(indice)
        }
        binding.rvListaVideo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListaVideo.adapter = videoAdapter
    }

    private fun cargarReceta(indice: Int) {
        val receta = recetas[indice]
        val video = receta.videos.firstOrNull()
        val ytId = video?.url ?: ""
        val mute   = if (estaMuteado) "&mute=1" else "&mute=0"
        binding.webViewVideo.loadUrl("https://www.youtube.com/embed/$ytId?autoplay=1$mute")
        binding.tvVideoTitulo.text    = receta.nombre
        val dificultadStr = when (receta.dificultad) {
            Dificultad.FACIL -> "Fácil"
            Dificultad.MEDIO -> "Medio"
            Dificultad.DIFICIL -> "Difícil"
        }
        binding.tvVideoCategoria.text = "${receta.categoria?.nombre ?: ""} · ${receta.porciones} porc. · $dificultadStr"
        binding.tvVideoRating.text    = "⭐ ${receta.calificacionPromedio}"
        binding.btnAnterior.isEnabled  = indice > 0
        binding.btnAnterior.alpha      = if (indice > 0) 1f else 0.4f
        binding.btnSiguiente.isEnabled = indice < recetas.size - 1
        binding.btnSiguiente.alpha     = if (indice < recetas.size - 1) 1f else 0.4f
        videoAdapter.setActivo(indice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class VideoListAdapter(
    private val recetas: List<Receta>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    private var indiceActivo = 0

    inner class ViewHolder(val binding: FragmentVideoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentVideoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receta = recetas[position]
        holder.binding.tvVideoItem.text = receta.nombre
        holder.binding.badgeNow.visibility = if (position == indiceActivo) View.VISIBLE else View.GONE
        Glide.with(holder.itemView.context).load(receta.imagenUrl).centerCrop().into(holder.binding.ivVideoItem)
        holder.itemView.setOnClickListener { onClick(position) }
    }

    override fun getItemCount() = recetas.size

    fun setActivo(indice: Int) {
        val anterior = indiceActivo
        indiceActivo = indice
        notifyItemChanged(anterior)
        notifyItemChanged(indice)
    }
}