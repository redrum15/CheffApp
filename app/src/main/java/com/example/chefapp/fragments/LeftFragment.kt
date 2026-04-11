package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.chefapp.MainActivity
import com.example.chefapp.R
import com.example.chefapp.databinding.FragmentLeftBinding
class LeftFragment : Fragment() {

    private var _binding: FragmentLeftBinding? = null
    private val binding get() = _binding!!

    private var itemActivo = "home"

    private lateinit var items: Map<String, Pair<LinearLayout, TextView>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items = mapOf(
            "perfil"  to Pair(binding.itemPerfil,  binding.labelPerfil),
            "fotos"   to Pair(binding.itemFotos,   binding.labelFotos),
            "video"   to Pair(binding.itemVideo,   binding.labelVideo),
            "web"     to Pair(binding.itemWeb,     binding.labelWeb),
            "botones" to Pair(binding.itemBotones, binding.labelBotones),
        )

        binding.itemPerfil.setOnClickListener  { navegarA("perfil") }
        binding.itemFotos.setOnClickListener   { navegarA("fotos") }
        binding.itemVideo.setOnClickListener   { navegarA("video") }
        binding.itemWeb.setOnClickListener     { navegarA("web") }
        binding.itemBotones.setOnClickListener { navegarA("botones") }

        actualizarResaltado()
    }

    private fun navegarA(tag: String) {
        val fragment = when (tag) {
            "perfil"  -> PerfilFragment()
            "fotos"   -> FotosFragment()
            "video"   -> VideoFragment()
            "web"     -> WebFragment()
            "botones" -> BotonesFragment()
            else -> PerfilFragment()
        }
        (activity as? MainActivity)?.cargarFragmentoDerecho(fragment, tag)
    }

    fun setItemActivo(tag: String) {
        itemActivo = tag
        actualizarResaltado()
    }

    private fun actualizarResaltado() {
        items.forEach { (tag, par) ->
            val (contenedor, label) = par
            if (tag == itemActivo) {
                contenedor.setBackgroundResource(R.drawable.bg_left_active)
                label.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
            } else {
                contenedor.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), android.R.color.transparent)
                )
                label.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.colorLeftInactive)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
