package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
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
	public void create(Book book)
	{
		bookElasticSearchService.save(bookMapper.toBookElasticSearch(book));
	}

	@Override
	public void deleteAllBooks()
	{
		bookElasticSearchService.deleteAllBooks();
	}
}
