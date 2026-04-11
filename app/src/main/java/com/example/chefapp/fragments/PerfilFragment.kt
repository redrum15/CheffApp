package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.chefapp.R
import com.example.chefapp.databinding.FragmentPerfilBinding
import com.example.chefapp.models.ObjetivoNutricional
import com.example.chefapp.models.TipoDieta
import com.example.chefapp.models.UsuarioActual
import com.google.android.material.chip.Chip

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val usuario = UsuarioActual.usuario

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load("https://randomuser.me/api/portraits/men/32.jpg")
            .circleCrop()
            .into(binding.ivFotoPerfil)

        mostrarPerfil()
        mostrarPreferencias()
        mostrarAlergias()

        binding.btnEditar.setOnClickListener {
            usuario.editarPerfil()
            binding.btnEditar.text = "Guardado ✓"
        }

        binding.btnBajar.setOnClickListener    { seleccionarObjetivo(ObjetivoNutricional.BAJAR_PESO) }
        binding.btnMantener.setOnClickListener { seleccionarObjetivo(ObjetivoNutricional.MANTENER_PESO) }
        binding.btnSubir.setOnClickListener    { seleccionarObjetivo(ObjetivoNutricional.SUBIR_PESO) }

        seleccionarObjetivo(usuario.objetivoNutricional)
    }

    private fun mostrarPerfil() {
        binding.tvNombre.text   = "${usuario.nombre} ${usuario.apellido}"
        binding.tvEdad.text     = "${usuario.edad} años"
        binding.tvPeso.text     = "${usuario.peso.toInt()} kg"
        binding.tvEstatura.text = "${usuario.estatura} m"
        val imc = usuario.calcularIMC()
        val categoria = when {
            imc < 18.5 -> "Bajo peso ⚠️"
            imc < 25   -> "Normal ✅"
            imc < 30   -> "Sobrepeso ⚠️"
            else       -> "Obesidad ❌"
        }
        binding.tvImc.text = "${String.format("%.1f", imc)} — $categoria"
    }

    private fun mostrarPreferencias() {
        binding.chipGroupPreferencias.removeAllViews()
        usuario.preferencias.forEach { pref ->
            val chip = Chip(requireContext()).apply {
                text = when (pref.tipo) {
                    TipoDieta.VEGETARIANA -> "Vegetariana 🥬"
                    TipoDieta.VEGANA -> "Vegana 🌱"
                    TipoDieta.KETO -> "Keto 🥩"
                    TipoDieta.SIN_GLUTEN -> "Sin gluten 🌾"
                    TipoDieta.SIN_LACTOSA -> "Sin lactosa 🥛"
                }
                isCloseIconVisible = false
                setChipBackgroundColorResource(R.color.colorCream)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.colorBrown))
            }
            binding.chipGroupPreferencias.addView(chip)
        }
    }

    private fun mostrarAlergias() {
        binding.chipGroupAlergias.removeAllViews()
        usuario.alergias.forEach { alergia ->
            val chip = Chip(requireContext()).apply {
                text = alergia.nombre
                isCloseIconVisible = false
                setChipBackgroundColorResource(R.color.colorRed)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
            }
            binding.chipGroupAlergias.addView(chip)
        }
    }

    private fun seleccionarObjetivo(objetivo: ObjetivoNutricional) {
        usuario.seleccionarObjetivo(objetivo)
        val tagActivo = when (objetivo) {
            ObjetivoNutricional.BAJAR_PESO -> "bajar"
            ObjetivoNutricional.MANTENER_PESO -> "mantener"
            ObjetivoNutricional.SUBIR_PESO -> "subir"
        }
        val botones = mapOf(
            "bajar"    to binding.btnBajar,
            "mantener" to binding.btnMantener,
            "subir"    to binding.btnSubir
        )
        botones.forEach { (key, btn) ->
            if (key == tagActivo) {
                btn.setBackgroundResource(R.drawable.bg_objetivo_active)
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOrange))
            } else {
                btn.setBackgroundResource(R.drawable.bg_objetivo_inactive)
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorGray))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}