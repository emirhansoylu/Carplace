package dev.duckbuddyy.carplace.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.duckbuddyy.carplace.databinding.ItemListingBinding
import dev.duckbuddyy.carplace.load
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem

class ListingAdapter(
    private val onItemClicked: (ListingResponseItem) -> Unit
) : PagingDataAdapter<ListingResponseItem, ListingAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemListingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListingResponseItem) = binding.apply {
            root.setOnClickListener { onItemClicked(item) }
            tvListingTitle.text = item.title
            tvListingPrice.text = item.actualPrice
            tvListingModelName.text = item.modelName
            item.getSizedPhoto()?.let { ivListing.load(it) }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<ListingResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ListingResponseItem,
            newItem: ListingResponseItem
        ) = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ListingResponseItem,
            newItem: ListingResponseItem
        ) = oldItem.id == newItem.id
    }
}