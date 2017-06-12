package net.nlacombe.booksuggestionws.webservice.impl;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.BookSuggestionRequest;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.api.webservice.BookWebService;
import net.nlacombe.booksuggestionws.constants.ElasticSearchConstants;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public Page<Book> getBookSuggestions(BookSuggestionRequest bookSuggestionRequest)
	{
		SearchQuery searchQuery = getSearchQuery(bookSuggestionRequest);

		org.springframework.data.domain.Page<BookElasticSearch> bookPage =
				elasticsearchTemplate.queryForPage(searchQuery, BookElasticSearch.class);

		return toPage(bookSuggestionRequest.getPageRequest(), bookPage);
	}

	private NativeSearchQuery getSearchQuery(BookSuggestionRequest bookSuggestionRequest)
	{
		return new NativeSearchQueryBuilder()
				.withIndices(ElasticSearchConstants.BOOK_SUGGESTION_INDEX_NAME)
				.withTypes(ElasticSearchConstants.BOOKS_TYPE_NAME)
				.withQuery(getBoostingQuery(bookSuggestionRequest.getOrderedPreferenceCriteria()))
				.withPageable(toSpringPageRequest(bookSuggestionRequest.getPageRequest()))
				.build();
	}

	private QueryBuilder getBoostingQuery(List<BookPreferenceCriterion> orderedPreferenceCriteria)
	{
		int numberOfCriteria = orderedPreferenceCriteria.size();

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		for (int criteriaIndex = 0; criteriaIndex < numberOfCriteria; criteriaIndex++)
		{
			BookPreferenceCriterion preferenceCriterion = orderedPreferenceCriteria.get(criteriaIndex);
			String fieldName = preferenceCriterion.getField().getFieldName();
			float rankingFactor = (float) Math.pow(2, numberOfCriteria - criteriaIndex);

			boolQuery = boolQuery.should(QueryBuilders.matchQuery(fieldName, preferenceCriterion.getValue()).boost(rankingFactor));
		}

		return boolQuery;
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
