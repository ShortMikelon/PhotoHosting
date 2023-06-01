package kz.aues.photohosting.presentations.main.tabs.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.PreviewItemBinding
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.aues.photohosting.presentations.main.tabs.images.PreviewsAdapter.PreviewViewHolder

class PreviewsAdapter(
    private val onPreviewClicked: ((PreviewEntity) -> Unit),
    private val onPreviewLongClicked: ((PreviewEntity) -> Unit)? = null
) : ListAdapter<PreviewEntity, PreviewViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        return PreviewViewHolder.create(parent, onPreviewClicked)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val preview = getItem(position)
        holder.bind(preview)
    }

    class PreviewViewHolder(
        private val binding: PreviewItemBinding,
        private val onPreviewClicked: (PreviewEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        fun bind(photoPreview: PreviewEntity) {
            binding.root.tag = photoPreview
            binding.imageView.load(data = photoPreview.uri) {
                placeholder(R.drawable.placeholder)
            }
        }

        override fun onClick(view: View) {
            val photo = view.tag as PreviewEntity
            onPreviewClicked(photo)
        }

        override fun onLongClick(view: View): Boolean {
            val photo = view.tag as PreviewEntity
            return true
        }

        companion object {

            fun create(parent: ViewGroup, onPreviewClicked: (PreviewEntity) -> Unit): PreviewViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.preview_item, parent, false)
                val binding = PreviewItemBinding.bind(view)

                val holder = PreviewViewHolder(binding, onPreviewClicked)
                binding.root.setOnClickListener(holder)
                binding.root.setOnLongClickListener(holder)

                return holder
            }
        }
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<PreviewEntity>() {

            override fun areItemsTheSame(oldItem: PreviewEntity, newItem: PreviewEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PreviewEntity, newItem: PreviewEntity): Boolean =
                oldItem == newItem

        }
    }
}