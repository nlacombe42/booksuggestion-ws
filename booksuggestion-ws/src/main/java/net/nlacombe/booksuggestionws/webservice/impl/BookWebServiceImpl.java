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
}
