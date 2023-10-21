package dev.duckbuddyy.carplace.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.duckbuddyy.carplace.databinding.ItemPropertyBinding
import dev.duckbuddyy.carplace.model.Property

class PropertyAdapter(
    private val properties: List<Property>,
) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPropertyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = properties.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = properties.getOrNull(position) ?: return
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemPropertyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) = binding.apply {
            if (property.name == null && property.value == null) {
                return@apply
            }
            tvSpecName.text = property.name?.uppercase()
            tvSpecDescription.text = property.value
        }
    }
}