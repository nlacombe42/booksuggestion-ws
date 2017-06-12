package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.BookSearchField;
import net.nlacombe.booksuggestionws.api.dto.PublicationEra;
import net.nlacombe.booksuggestionws.constants.ElasticSearchConstants;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.service.BookService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService
{
	@Inject
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public Page<BookElasticSearch> getBookSuggestions(PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria)
	{
		SearchQuery searchQuery = getSearchQuery(pageRequest, preferenceCriteria);

		return elasticsearchTemplate.queryForPage(searchQuery, BookElasticSearch.class);
	}

	private NativeSearchQuery getSearchQuery(org.springframework.data.domain.PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria)
	{
		return new NativeSearchQueryBuilder()
				.withIndices(ElasticSearchConstants.BOOK_SUGGESTION_INDEX_NAME)
				.withTypes(ElasticSearchConstants.BOOKS_TYPE_NAME)
				.withQuery(getBoostingQuery(preferenceCriteria))
				.withPageable(pageRequest)
				.build();
	}

	private QueryBuilder getBoostingQuery(List<BookPreferenceCriterion> orderedPreferenceCriteria)
	{
		int numberOfCriteria = orderedPreferenceCriteria.size();

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		for (int criteriaIndex = 0; criteriaIndex < numberOfCriteria; criteriaIndex++)
		{
			BookPreferenceCriterion preferenceCriterion = orderedPreferenceCriteria.get(criteriaIndex);
			BookSearchField field = preferenceCriterion.getField();
			float rankingFactor = (float) Math.pow(2, numberOfCriteria - criteriaIndex);

			if (BookSearchField.PUBLICATION_ERA.equals(field))
				boolQuery = getBoostQueryByEra(boolQuery, PublicationEra.valueOf(preferenceCriterion.getValue()), rankingFactor);
			else
				boolQuery = getBoostQueryByExactText(boolQuery, field.getFieldName(), preferenceCriterion.getValue(), rankingFactor);
		}

		return boolQuery;
	}

	private BoolQueryBuilder getBoostQueryByEra(BoolQueryBuilder boolQuery, PublicationEra publicationEra, float rankingFactor)
	{
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(BookSearchField.PUBLICATION_ERA.getFieldName());

		if (PublicationEra.MODERN.equals(publicationEra))
			rangeQuery = rangeQuery.gte(PublicationEra.MODERN.getStartYear());
		else
			rangeQuery = rangeQuery.lt(PublicationEra.MODERN.getStartYear());

		return boolQuery.should(rangeQuery.boost(rankingFactor));
	}

	private BoolQueryBuilder getBoostQueryByExactText(BoolQueryBuilder boolQuery, String fieldName, String value, float rankingFactor)
	{
		return boolQuery.should(QueryBuilders.matchQuery(fieldName, value).boost(rankingFactor));
	}
}
