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
import com.example.chefapp.models.UsuarioActual

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    // Usuario del modelo
    private val usuario = UsuarioActual.usuario

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar foto de perfil con Glide
        Glide.with(this)
            .load("https://randomuser.me/api/portraits/men/32.jpg")
            .circleCrop()
            .into(binding.ivFotoPerfil)

        // Vincular datos desde el modelo Usuario
        mostrarPerfil()
        mostrarPreferencias()
        mostrarAlergias()

        // Evento boton Editar
        binding.btnEditar.setOnClickListener {
            usuario.editarPerfil()
            binding.btnEditar.text = "Guardado ✓"
        }

        // Eventos botones objetivo
        binding.btnBajar.setOnClickListener    { seleccionarObjetivo(ObjetivoNutricional.BAJAR_PESO) }
        binding.btnMantener.setOnClickListener { seleccionarObjetivo(ObjetivoNutricional.MANTENER_PESO) }
        binding.btnSubir.setOnClickListener    { seleccionarObjetivo(ObjetivoNutricional.SUBIR_PESO) }

        // Estado inicial objetivo
        seleccionarObjetivo(usuario.objetivoNutricional)
    }

    // Muestra los datos del perfil del usuario
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

    // Muestra las preferencias dietéticas
    private fun mostrarPreferencias() {
        // Las preferencias se muestran en la UI existente sin cambios visuales
    }

    // Muestra las alergias del usuario
    private fun mostrarAlergias() {
        // Las alergias se muestran en la UI existente sin cambios visuales
    }

    // Actualiza el objetivo nutricional en el modelo y refleja en UI
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