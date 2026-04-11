package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.chefapp.databinding.FragmentBotonesBinding
import com.example.chefapp.models.*

class BotonesFragment : Fragment() {

    private var _binding: FragmentBotonesBinding? = null
    private val binding get() = _binding!!

    // Receta activa para cálculos (primera por defecto)
    private var recetaActual: Receta = RecetaData.recetas.first()

    // Variables vinculadas con widgets
    private var porciones = recetaActual.porciones
    private var unidadActual = UnidadMedida.GRAMOS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBotonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. RatingBar — calificar receta
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            calificarReceta(rating.toInt())
        }

        // 2. Switch — unidades de medida
        binding.switchUnidades.setOnCheckedChangeListener { _, isChecked ->
            cambiarUnidades(isChecked)
        }

        // 3. RadioGroup — nivel de dificultad
        binding.radioGroupDificultad.setOnCheckedChangeListener { _, checkedId ->
            seleccionarRestricciones()
            val dificultad = when (checkedId) {
                binding.radioFacil.id   -> "Fácil ✅"
                binding.radioMedio.id   -> "Medio ⚠️"
                binding.radioDificil.id -> "Difícil 🔥"
                else -> ""
            }
            binding.tvDificultadSeleccionada.text = dificultad
        }

        // 4. SeekBar — porciones
        binding.seekBarPorciones.max = 9 // 1 a 10
        binding.seekBarPorciones.progress = porciones - 1
        binding.tvPorciones.text = "$porciones porciones"

        binding.seekBarPorciones.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                porciones = progress + 1
                binding.tvPorciones.text = "$porciones porciones"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Boton Calcular ingredientes
        binding.btnCalcular.setOnClickListener {
            calcularIngredientes()
        }

        // 5. CheckBox — restricciones alimentarias
        binding.checkGluten.setOnCheckedChangeListener { _, _ -> seleccionarRestricciones() }
        binding.checkVegano.setOnCheckedChangeListener { _, _ -> seleccionarRestricciones() }
        binding.checkLactosa.setOnCheckedChangeListener { _, _ -> seleccionarRestricciones() }
        binding.checkMariscos.setOnCheckedChangeListener { _, _ -> seleccionarRestricciones() }
    }

    // Califica la receta actual usando el modelo
    private fun calificarReceta(valor: Int) {
        val etiqueta = when (valor) {
            1 -> "Malo 😞"
            2 -> "Regular 😐"
            3 -> "Bueno 🙂"
            4 -> "Muy bueno 😊"
            5 -> "Excelente 🤩"
            else -> ""
        }
        binding.tvRatingLabel.text = etiqueta
        UsuarioActual.usuario.calificarReceta(recetaActual, valor)
    }

    // Cambia las unidades de medida usando el enum del modelo
    private fun cambiarUnidades(usarOnzas: Boolean) {
        unidadActual = if (usarOnzas) UnidadMedida.ONZAS else UnidadMedida.GRAMOS
        binding.tvUnidadActiva.text = if (unidadActual == UnidadMedida.GRAMOS) "Gramos / °C" else "Onzas / °F"
    }

    // Actualiza restricciones en el modelo de usuario
    private fun seleccionarRestricciones() {
        val usuario = UsuarioActual.usuario
        usuario.alergias.clear()
        if (binding.checkGluten.isChecked)   usuario.agregarAlergia(Alergia(1, "Gluten"))
        if (binding.checkVegano.isChecked)   usuario.agregarPreferencia(PreferenciaDietetica(1, TipoDieta.VEGANA))
        if (binding.checkLactosa.isChecked)  usuario.agregarAlergia(Alergia(2, "Lactosa"))
        if (binding.checkMariscos.isChecked) usuario.agregarAlergia(Alergia(3, "Mariscos"))
    }

    // Calcula ingredientes escalados usando el modelo Receta
    private fun calcularIngredientes() {
        val recetaAjustada = recetaActual.ajustarPorciones(porciones)

        val resultado = if (unidadActual == UnidadMedida.GRAMOS) {
            recetaAjustada.ingredientes.joinToString(" | ") { "${it.nombre}: ${it.cantidad.toInt()}g" }
        } else {
            recetaAjustada.ingredientes.joinToString(" | ") { ingrediente ->
                val enOnzas = ingrediente.cantidad * 0.035274f
                "${ingrediente.nombre}: ${String.format("%.1f", enOnzas)}oz"
            }
        }

        binding.tvResultado.text = resultado
        binding.tvResultado.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}