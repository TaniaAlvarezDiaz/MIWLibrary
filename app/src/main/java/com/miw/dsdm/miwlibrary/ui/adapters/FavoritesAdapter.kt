package com.miw.dsdm.miwlibrary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.Book
import kotlinx.android.synthetic.main.favorite_card_item.view.*
import kotlinx.android.synthetic.main.library_card_item.view.*

class FavoritesAdapter(
    val listener: IFavoritesAdapter, val items: MutableList<Book>, val itemClick: (Book) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView, val itemClick: (Book) -> Unit) :
        RecyclerView.ViewHolder(cardView) {
        fun bind(listener: IFavoritesAdapter, book: Book) {
            with(book) {
                if (imagePath != null && imagePath.isNotEmpty())
                    Glide.with(itemView).load(imagePath).into(itemView.favorite_card_item_image)
                itemView.favorite_card_item_title.text = title
                itemView.favorite_card_item_author.text = author
                itemView.favorite_card_item_summary.text = summary
                //Favorite icon
                itemView.favorite_card_item_delete.setOnClickListener {
                    listener.deleteFavoriteBook(this)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_card_item, parent, false) as CardView
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) {
        holder.bind(listener, items.get(position))
    }

    //Interface
    interface IFavoritesAdapter {
        fun deleteFavoriteBook(book: Book)
    }
}