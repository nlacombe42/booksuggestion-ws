package net.nlacombe.booksuggestionws.webservice.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookSuggestionRequest;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.api.webservice.BookWebService;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookWebServiceImpl implements BookWebService
{
	@Inject
	private BookService bookService;

	@Override
	public Page<Book> getBookSuggestions(BookSuggestionRequest bookSuggestionRequest)
	{
		org.springframework.data.domain.PageRequest pageRequest = toSpringPageRequest(bookSuggestionRequest.getPageRequest());

		org.springframework.data.domain.Page<BookElasticSearch> booksPage = bookService.getBookSuggestions(pageRequest, bookSuggestionRequest.getOrderedPreferenceCriteria());

		return toPage(bookSuggestionRequest.getPageRequest(), booksPage);
	}



	private Page<Book> toPage(PageRequest pageRequest, org.springframework.data.domain.Page<BookElasticSearch> bookPage)
	{
		List<Book> books =
				bookPage.getContent().stream()
						.map(this::toBook)
						.collect(Collectors.toList());

		return new Page<>(pageRequest, bookPage.getTotalElements(), books);
	}

	private Book toBook(BookElasticSearch elasticSearchBook)
	{
		Book book = new Book();

		BeanUtils.copyProperties(elasticSearchBook, book);

		return book;
	}

	private org.springframework.data.domain.PageRequest toSpringPageRequest(PageRequest pageRequest)
	{
		return new org.springframework.data.domain.PageRequest((int) pageRequest.getPageNumber(), (int) pageRequest.getElementsPerPage());
	}
}
