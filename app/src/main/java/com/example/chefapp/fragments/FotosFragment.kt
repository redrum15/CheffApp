package com.example.chefapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chefapp.R
import com.example.chefapp.databinding.FragmentFotosBinding
import com.example.chefapp.databinding.ItemRecipeBinding
import com.example.chefapp.helpers.GaleriaRecetas
import com.example.chefapp.models.Dificultad
import com.example.chefapp.models.Receta
import com.example.chefapp.models.RecetaData


class FotosFragment : Fragment() {

    private var _binding: FragmentFotosBinding? = null
    private val binding get() = _binding!!

    private val galeria = GaleriaRecetas()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galeria.mostrarGaleria()

        val adapter = RecipeAdapter(galeria.recetas) { receta ->
            galeria.seleccionarReceta(receta)
            mostrarDetalle(receta)
        }
        binding.rvRecetas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecetas.adapter = adapter

        mostrarDetalle(galeria.recetas.first())
    }

    private fun mostrarDetalle(receta: Receta) {
        binding.tvDetalleTitulo.text     = receta.nombre
        binding.tvDetalleCategoria.text  = receta.categoria?.nombre ?: ""
        binding.tvDetalleTiempo.text     = "${receta.porciones} porciones"
        binding.tvDetalleDificultad.text = when (receta.dificultad) {
            Dificultad.FACIL -> "Fácil"
            Dificultad.MEDIO -> "Medio"
            Dificultad.DIFICIL -> "Difícil"
        }
        binding.tvDetalleDesc.text       = receta.descripcion
        binding.tvDetalleRating.text     = "⭐ ${receta.calificacionPromedio}"

        Glide.with(this)
            .load(receta.imagenUrl)
            .centerCrop()
            .into(binding.ivDetalleImagen)

        binding.btnGuardar.setOnClickListener {
            binding.btnGuardar.text = "✓ Guardado"
            binding.btnGuardar.isEnabled = false
        }

        binding.btnVerReceta.setOnClickListener {
            binding.btnVerReceta.text = "Abriendo..."
        }
    }

    fun navegarImagenSiguiente() {
        galeria.siguiente()?.let { mostrarDetalle(it) }
    }

    fun navegarImagenAnterior() {
        galeria.anterior()?.let { mostrarDetalle(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


class RecipeAdapter(
    private val recetas: List<Receta>,
    private val onClick: (Receta) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var seleccionado = 0

    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receta = recetas[position]
        val ctx    = holder.itemView.context

        holder.binding.tvItemTitulo.text     = receta.nombre
        holder.binding.tvItemCategoria.text  = receta.categoria?.nombre ?: ""
        holder.binding.tvItemTiempo.text     = "${receta.porciones} porciones"
        holder.binding.tvItemDificultad.text = when (receta.dificultad) {
            Dificultad.FACIL -> "Fácil"
            Dificultad.MEDIO -> "Medio"
            Dificultad.DIFICIL -> "Difícil"
        }
        holder.binding.tvItemRating.text     = "⭐ ${receta.calificacionPromedio}"

        Glide.with(ctx)
            .load(receta.imagenUrl)
            .centerCrop()
            .into(holder.binding.ivItemThumbnail)

        holder.binding.root.setBackgroundResource(
            if (position == seleccionado) R.drawable.bg_item_active else R.drawable.bg_item_normal
        )

        holder.binding.root.setOnClickListener {
            val anterior = seleccionado
            seleccionado = position
            notifyItemChanged(anterior)
            notifyItemChanged(position)
            onClick(receta)
        }
    }

    override fun getItemCount() = recetas.size
}