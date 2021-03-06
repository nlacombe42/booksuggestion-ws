package net.nlacombe.booksuggestionws.service;

import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookElasticSearchService
{
	Page<BookElasticSearch> getBookSuggestions(PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria);

	BookElasticSearch save(BookElasticSearch book);

	void deleteBook(int bookId);

	void deleteAllBooks();
}
