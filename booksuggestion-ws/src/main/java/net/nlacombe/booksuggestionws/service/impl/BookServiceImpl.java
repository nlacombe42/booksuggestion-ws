package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.data.entity.BookEntity;
import net.nlacombe.booksuggestionws.repository.jpa.BookRepository;
import net.nlacombe.booksuggestionws.service.BookElasticSearchService;
import net.nlacombe.booksuggestionws.service.BookService;
import net.nlacombe.booksuggestionws.utils.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService
{
	@Inject
	private BookRepository bookRepository;

	@Inject
	private BookElasticSearchService bookElasticSearchService;

	@Inject
	private BookMapper bookMapper;

	@Override
	public Page<Book> getBookSuggestions(PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria)
	{
		org.springframework.data.domain.PageRequest springPageRequest = bookMapper.toSpringPageRequest(pageRequest);

		org.springframework.data.domain.Page<BookElasticSearch> booksPage = bookElasticSearchService.getBookSuggestions(springPageRequest, preferenceCriteria);

		return bookMapper.toPage(pageRequest, booksPage);
	}

	@Override
	public Book create(Book book)
	{
		return saveAndReindex(book);
	}

	@Override
	public Book updateBook(Book book)
	{
		return saveAndReindex(book);
	}

	@Override
	public void deleteBook(int bookId)
	{
		bookRepository.delete(bookId);
		bookElasticSearchService.deleteBook(bookId);
	}

	@Override
	public void deleteAllBooks()
	{
		bookRepository.deleteAll();
		bookElasticSearchService.deleteAllBooks();
	}

	private Book saveAndReindex(Book book)
	{
		book = saveBook(book);

		reindexInElasticSearch(book);

		return book;
	}

	private Book reindexInElasticSearch(Book book)
	{
		BookElasticSearch bookElasticSearch = bookMapper.toBookElasticSearch(book);

		return bookMapper.toBook(bookElasticSearchService.save(bookElasticSearch));
	}

	private Book saveBook(Book book)
	{
		BookEntity bookEntity = bookMapper.toBookEntity(book);
		bookEntity = bookRepository.save(bookEntity);
		book = bookMapper.toBook(bookEntity);
		return book;
	}
}
