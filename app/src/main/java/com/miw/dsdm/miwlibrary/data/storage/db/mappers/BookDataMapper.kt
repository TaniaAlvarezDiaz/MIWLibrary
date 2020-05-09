package com.miw.dsdm.miwlibrary.data.storage.db.mappers

import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookEntity
import com.miw.dsdm.miwlibrary.model.Book

object BookDataMapper {

    /**
     * Function to convert each book in the list that is passed by parameter into a domain object
     */
    fun convertToDomain(books: List<BookEntity>) = books.map { convertBookToDomain(it) }

    /**
     * Function to convert the book passed by parameter to a domain object (book)
     */
    private fun convertBookToDomain(book: BookEntity) = Book(book.id,
        book.imagePath, book.title, book.author, book.language, book.content, book.summary,
        book.publisher, book.publicationYear, book.detailsUrl, emptyList()
    )

    fun convertFromDomain(books: List<Book>) = books.map { convertBookFromDomain(it) }

    private fun convertBookFromDomain(book: Book) = BookEntity(book.id,
        book.imagePath, book.title, book.author, book.language, book.content, book.summary,
        book.publisher, book.publicationYear, book.detailsUrl
    )


}