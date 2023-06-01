package kz.aues.photohosting.presentations.main.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.ItemProfileImageBinding
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity

class ProfileImagesAdapter :
    ListAdapter<PreviewEntity, ProfileImagesAdapter.PreviewViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        return PreviewViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PreviewViewHolder(
        private val binding: ItemProfileImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PreviewEntity) {
            with(binding) {
                imageView.tag = item
                imageView.load(item.uri) {
                    placeholder(R.drawable.placeholder)
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): PreviewViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_profile_image, parent, false)
                val binding = ItemProfileImageBinding.bind(view)

                return PreviewViewHolder(binding)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PreviewEntity>() {

            override fun areItemsTheSame(oldItem: PreviewEntity, newItem: PreviewEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PreviewEntity,
                newItem: PreviewEntity
            ): Boolean =
                oldItem == newItem
        }
    }
}