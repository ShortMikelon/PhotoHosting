package kz.aues.photohosting.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.CommentViewBinding
import kz.samsungcampus.common.extensions.getTimeStamp

class CommentView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: CommentViewBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.comment_view, this, true)
        binding = CommentViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommentView, defStyleAttr, defStyleRes)

        with(binding) {
            val authorName = typedArray.getString(R.styleable.CommentView_authorName) ?: "author"
            authorNameTextView.text = context.getString(R.string.author_name, authorName)
            val createdAt = typedArray.getString(R.styleable.CommentView_createdAt) ?: 0L.getTimeStamp()
            createdAtTextView.text = createdAt
            val comment = typedArray.getString(R.styleable.CommentView_comment) ?: "default text"
            commentTextView.text = comment
        }

        typedArray.recycle()
    }
}