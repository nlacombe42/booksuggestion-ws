package net.nlacombe.booksuggestionws.webservice.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookSuggestionRequest;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.api.webservice.BookWebService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookWebServiceImpl implements BookWebService
{
	@Override
	public Page<Book> getBookSuggestions(BookSuggestionRequest bookSuggestionRequest)
	{
		Book book = new Book("Isaac Asimov", "Sci-Fi", 200, 1952, 4.5f);
		List<Book> books = Collections.singletonList(book);

		return new Page<>(new PageRequest(0, 1), 1, books);
	}
}
