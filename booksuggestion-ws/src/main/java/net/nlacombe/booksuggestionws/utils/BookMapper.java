package net.nlacombe.booksuggestionws.utils;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper
{
	public Page<Book> toPage(PageRequest pageRequest, org.springframework.data.domain.Page<BookElasticSearch> bookPage)
	{
		List<Book> books =
				bookPage.getContent().stream()
						.map(this::toBook)
						.collect(Collectors.toList());

		return new Page<>(pageRequest, bookPage.getTotalElements(), books);
	}

	public Book toBook(BookElasticSearch elasticSearchBook)
	{
		Book book = new Book();

		BeanUtils.copyProperties(elasticSearchBook, book);

		return book;
	}

	public org.springframework.data.domain.PageRequest toSpringPageRequest(PageRequest pageRequest)
	{
		return new org.springframework.data.domain.PageRequest((int) pageRequest.getPageNumber(), (int) pageRequest.getElementsPerPage());
	}

	public BookElasticSearch toBookElasticSearch(Book book)
	{
		BookElasticSearch elasticSearchBook = new BookElasticSearch();

		BeanUtils.copyProperties(book, elasticSearchBook);

		return elasticSearchBook;
	}
}
