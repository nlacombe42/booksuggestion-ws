package net.nlacombe.booksuggestionws.webservice.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookSuggestionRequest;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.webservice.BookWebService;
import net.nlacombe.booksuggestionws.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class BookWebServiceImpl implements BookWebService
{
	@Inject
	private BookService bookService;

	@Override
	public Page<Book> getBookSuggestions(BookSuggestionRequest bookSuggestionRequest)
	{
		return bookService.getBookSuggestions(bookSuggestionRequest.getPageRequest(), bookSuggestionRequest.getOrderedPreferenceCriteria());
	}

	@Override
	public Book createBook(Book book)
	{
		book.setBookId(0);

		return bookService.create(book);
	}

	@Override
	public Book updateBook(int bookId, Book book)
	{
		if (book.getBookId() != bookId)
			throw new IllegalArgumentException("bookId in body not matching bookId in URL");

		return bookService.updateBook(book);
	}

	@Override
	public void deleteBook(int bookId)
	{
		bookService.deleteBook(bookId);
	}
}
