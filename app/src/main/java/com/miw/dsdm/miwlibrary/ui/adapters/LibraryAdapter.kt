package com.miw.dsdm.miwlibrary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.Book
import kotlinx.android.synthetic.main.library_card_item.view.*

class LibraryAdapter(
    val items: MutableList<Book>, val
    itemClick: (Book) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.ViewHolder>(), Filterable {

    private var filterList = mutableListOf<Book>()

    init {
        filterList = items
    }

    class ViewHolder(val cardView: CardView, val itemClick: (Book) -> Unit) :
        RecyclerView.ViewHolder(cardView) {
        fun bind(book: Book) {
            with(book) {
                if (imagePath != null && imagePath.isNotEmpty())
                    Glide.with(itemView).load(imagePath).into(itemView.library_card_item_image)
                itemView.library_card_item_title.text = title
                itemView.library_card_item_author.text = author
                itemView.library_card_item_summary.text = summary

                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_card_item, parent, false) as CardView
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: LibraryAdapter.ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val valSearch = constraint.toString()
                //Filter by title or author
                filterList = (if (valSearch.isEmpty()) items else filterByTitleOrAuthor(valSearch.toLowerCase())) as MutableList<Book>

                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as MutableList<Book>
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Function that filters books by title or author
     */
    private fun filterByTitleOrAuthor(valSearch: CharSequence): List<Book> {
        val res = mutableListOf<Book>()
        for (book in items) {
            with(book) {
                if ((title != null && title.toLowerCase().contains(valSearch)) or
                    (author != null && author.toLowerCase().contains(valSearch))
                ) {
                    res.add(this)
                }
            }
        }
        return res
    }
}