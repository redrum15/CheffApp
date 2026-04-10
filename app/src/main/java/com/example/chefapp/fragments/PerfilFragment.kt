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

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    // Variable objetivo activo
    private var objetivoActivo = "mantener"

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

        // Vincular datos con IDs
        binding.tvNombre.text       = "Marco Ríos"
        binding.tvEdad.text         = "28 años"
        binding.tvPeso.text         = "72 kg"
        binding.tvEstatura.text     = "1.75 m"
        binding.tvImc.text          = "23.5 — Normal ✅"

        // Evento boton Editar
        binding.btnEditar.setOnClickListener {
            // En implementacion real abriria un Dialog o Activity de edicion
            binding.btnEditar.text = "Guardado ✓"
        }

        // Eventos botones objetivo
        binding.btnBajar.setOnClickListener    { setObjetivo("bajar") }
        binding.btnMantener.setOnClickListener { setObjetivo("mantener") }
        binding.btnSubir.setOnClickListener    { setObjetivo("subir") }

        // Estado inicial objetivo
        setObjetivo("mantener")
    }

    // Actualiza el boton de objetivo activo
    private fun setObjetivo(objetivo: String) {
        objetivoActivo = objetivo
        val botones = mapOf(
            "bajar"    to binding.btnBajar,
            "mantener" to binding.btnMantener,
            "subir"    to binding.btnSubir
        )
        botones.forEach { (key, btn) ->
            if (key == objetivo) {
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