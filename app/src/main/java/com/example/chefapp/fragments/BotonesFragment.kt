package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.chefapp.databinding.FragmentBotonesBinding

class BotonesFragment : Fragment() {

    private var _binding: FragmentBotonesBinding? = null
    private val binding get() = _binding!!

    // Variables vinculadas con widgets
    private var porciones = 4
    private var esMetrico = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBotonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. RatingBar — calificar receta
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val etiqueta = when (rating.toInt()) {
                1 -> "Malo 😞"
                2 -> "Regular 😐"
                3 -> "Bueno 🙂"
                4 -> "Muy bueno 😊"
                5 -> "Excelente 🤩"
                else -> ""
            }
            binding.tvRatingLabel.text = etiqueta
        }

        // 2. Switch — unidades de medida
        binding.switchUnidades.setOnCheckedChangeListener { _, isChecked ->
            esMetrico = !isChecked
            binding.tvUnidadActiva.text = if (esMetrico) "Gramos / °C" else "Onzas / °F"
        }

        // 3. RadioGroup — nivel de dificultad
        binding.radioGroupDificultad.setOnCheckedChangeListener { _, checkedId ->
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
        binding.seekBarPorciones.progress = 3 // default 4
        binding.tvPorciones.text = "4 porciones"

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

        // 5. CheckBox — restricciones alimentarias (solo eventos de estado)
        binding.checkGluten.setOnCheckedChangeListener { _, isChecked ->
            // Logica de filtrado por gluten se aplicaria en FotosFragment
        }
        binding.checkVegano.setOnCheckedChangeListener { _, isChecked -> }
        binding.checkLactosa.setOnCheckedChangeListener { _, isChecked -> }
        binding.checkMariscos.setOnCheckedChangeListener { _, isChecked -> }
    }

    // Calcula ingredientes escalados segun porciones
    private fun calcularIngredientes() {
        val base = 4 // porciones base de la receta
        val factor = porciones.toDouble() / base

        val pasta    = (200 * factor).toInt()
        val huevos   = (4  * factor).toInt()
        val queso    = (100 * factor).toInt()
        val guanciale = (150 * factor).toInt()

        val resultado = if (esMetrico) {
            "Pasta: ${pasta}g | Huevos: $huevos | Queso: ${queso}g | Guanciale: ${guanciale}g"
        } else {
            val pastaOz = String.format("%.1f", pasta * 0.035)
            "Pasta: ${pastaOz}oz | Huevos: $huevos | Queso: ${(queso*0.035).toInt()}oz | Guanciale: ${(guanciale*0.035).toInt()}oz"
        }

        binding.tvResultado.text = resultado
        binding.tvResultado.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}