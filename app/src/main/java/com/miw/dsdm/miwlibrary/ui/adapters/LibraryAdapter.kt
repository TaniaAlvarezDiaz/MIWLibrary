package com.miw.dsdm.miwlibrary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.Book
import kotlinx.android.synthetic.main.library_card_item.view.*

class LibraryAdapter(val items: List<Book>, val itemClick: (Book) -> Unit) :
    RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView, val itemClick: (Book) -> Unit) :
        RecyclerView.ViewHolder(cardView) {
        fun bind(book: Book) {
            with(book) {
                itemView.setOnClickListener { itemClick(this) }
                //TODO complete image
                itemView.library_card_item_title.text = title
                itemView.library_card_item_author.text = author
                itemView.library_card_item_description.text = description
                itemView.library_card_item_favorite.setImageResource(
                    if (favorite) R.drawable.ic_favorites_checked else R.drawable.ic_favorites
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_card_item, parent, false) as CardView
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LibraryAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}