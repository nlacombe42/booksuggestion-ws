package net.nlacombe.booksuggestionws.service;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;

import java.util.List;

public interface BookService
{
	Page<Book> getBookSuggestions(PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria);

	Book create(Book book);

	Book updateBook(Book book);

	void deleteBook(int bookId);

	void deleteAllBooks();
}
