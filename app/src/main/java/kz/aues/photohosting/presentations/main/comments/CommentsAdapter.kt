package kz.aues.photohosting.presentations.main.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.ItemCommentBinding
import kz.aues.photohosting.domain.main.comments.entities.CommentEntity
import kz.samsungcampus.common.extensions.getYearMonthDay

class CommentsAdapter : ListAdapter<CommentEntity, CommentsAdapter.CommentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CommentViewHolder(
        private val binding: ItemCommentBinding
    ): ViewHolder(binding.root) {
        fun bind(item: CommentEntity) {
            with (binding) {
                authorNameTextView.text = item.author
                createdAtTextView.text = item.createdAt.getYearMonthDay()
                commentTextView.text = item.text
            }
        }

        companion object {
            fun create(parent: ViewGroup): CommentViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
                val binding = ItemCommentBinding.bind(view)
                return CommentViewHolder(binding)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentEntity>() {
            override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean =
                oldItem == newItem

        }
    }
}