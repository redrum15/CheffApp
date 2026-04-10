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
import com.example.chefapp.models.Recipe
import com.example.chefapp.models.RecipeData


class FotosFragment : Fragment() {

    private var _binding: FragmentFotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView con adapter
        val adapter = RecipeAdapter(RecipeData.recipes) { receta ->
            mostrarDetalle(receta)
        }
        binding.rvRecetas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecetas.adapter = adapter

        // Mostrar primera receta por defecto
        mostrarDetalle(RecipeData.recipes.first())
    }

    // Actualiza el panel de detalle con la receta seleccionada
    private fun mostrarDetalle(receta: Recipe) {
        binding.tvDetalleTitulo.text     = receta.title
        binding.tvDetalleCategoria.text  = receta.category
        binding.tvDetalleTiempo.text     = receta.time
        binding.tvDetalleDificultad.text = receta.difficulty
        binding.tvDetalleDesc.text       = receta.description
        binding.tvDetalleRating.text     = "⭐ ${receta.rating}"

        Glide.with(this)
            .load(receta.imageUrl)
            .centerCrop()
            .into(binding.ivDetalleImagen)

        // Evento boton Guardar
        binding.btnGuardar.setOnClickListener {
            binding.btnGuardar.text = "✓ Guardado"
            binding.btnGuardar.isEnabled = false
        }

        // Evento boton Ver receta
        binding.btnVerReceta.setOnClickListener {
            binding.btnVerReceta.text = "Abriendo..."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// ── Adapter RecyclerView ─────────────────────────────────────────────────────
class RecipeAdapter(
    private val recetas: List<Recipe>,
    private val onClick: (Recipe) -> Unit
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

        // Vincular datos con IDs del item_recipe.xml
        holder.binding.tvItemTitulo.text     = receta.title
        holder.binding.tvItemCategoria.text  = receta.category
        holder.binding.tvItemTiempo.text     = receta.time
        holder.binding.tvItemDificultad.text = receta.difficulty
        holder.binding.tvItemRating.text     = "⭐ ${receta.rating}"

        // Cargar thumbnail con Glide
        Glide.with(ctx)
            .load(receta.imageUrl)
            .centerCrop()
            .into(holder.binding.ivItemThumbnail)

        // Resaltar item seleccionado
        holder.binding.root.setBackgroundResource(
            if (position == seleccionado) R.drawable.bg_item_active else R.drawable.bg_item_normal
        )

        // Evento clic en item
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