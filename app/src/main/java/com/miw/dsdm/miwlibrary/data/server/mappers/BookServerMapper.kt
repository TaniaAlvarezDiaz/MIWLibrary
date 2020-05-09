package com.miw.dsdm.miwlibrary.data.server.mappers

import com.miw.dsdm.miwlibrary.data.server.BookResponse
import com.miw.dsdm.miwlibrary.model.Book

object BookServerMapper {

    /**
     * Function to convert each book in the list that is passed by parameter into a domain object
     */
    fun convertToDomain(books: List<BookResponse>) = books.map { convertBookToDomain(it) }

    /**
     * Function to convert the book passed by parameter to a domain object (book)
     */
    private fun convertBookToDomain(book: BookResponse) = Book(
        book.id.toLong(), book.imagePath, book.title, book.author, book.language, book.content, book.summary,
        book.publisher, book.publicationYear, book.detailsUrl, CategoryServerMapper.convertToDomain(book.categories)
    )

}