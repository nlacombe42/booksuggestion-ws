package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.BookSearchField;
import net.nlacombe.booksuggestionws.api.dto.PublicationEra;
import net.nlacombe.booksuggestionws.constants.ElasticSearchConstants;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.repository.elasticsearch.BookElasticSearchRepository;
import net.nlacombe.booksuggestionws.service.BookElasticSearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.fieldvaluefactor.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.weight.WeightBuilder;
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
public class BookElasticSearchServiceImpl implements BookElasticSearchService
{
	@Inject
	private ElasticsearchTemplate elasticsearchTemplate;

	@Inject
	private BookElasticSearchRepository bookElasticSearchRepository;

	@Override
	public Page<BookElasticSearch> getBookSuggestions(PageRequest pageRequest, List<BookPreferenceCriterion> preferenceCriteria)
	{
		SearchQuery searchQuery = getSearchQuery(pageRequest, preferenceCriteria);

		return elasticsearchTemplate.queryForPage(searchQuery, BookElasticSearch.class);
	}

	@Override
	public BookElasticSearch save(BookElasticSearch book)
	{
		return bookElasticSearchRepository.save(book);
	}

	@Override
	public void deleteAllBooks()
	{
		bookElasticSearchRepository.deleteAll();
	}

	@Override
	public void deleteBook(int bookId)
	{
		bookElasticSearchRepository.deleteByBookId(bookId);
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
		FunctionScoreQueryBuilder scoreQuery = QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery());

		scoreQuery = addPreferenceCriteriaScoreFunctions(scoreQuery, orderedPreferenceCriteria);
		scoreQuery = addRatingScoringFunction(scoreQuery);

		return scoreQuery;
	}

	private FunctionScoreQueryBuilder addPreferenceCriteriaScoreFunctions(FunctionScoreQueryBuilder scoreQuery, List<BookPreferenceCriterion> orderedPreferenceCriteria)
	{
		int numberOfCriteria = orderedPreferenceCriteria.size();

		for (int criteriaIndex = 0; criteriaIndex < numberOfCriteria; criteriaIndex++)
		{
			BookPreferenceCriterion preferenceCriterion = orderedPreferenceCriteria.get(criteriaIndex);
			scoreQuery = addPreferenceCriterionScoreFunction(scoreQuery, numberOfCriteria, preferenceCriterion, criteriaIndex);
		}

		return scoreQuery;
	}

	private FunctionScoreQueryBuilder addPreferenceCriterionScoreFunction(FunctionScoreQueryBuilder scoreQuery, int numberOfCriteria, BookPreferenceCriterion preferenceCriterion, int criteriaIndex)
	{
		QueryBuilder criterionQuery = getCriterionQuery(preferenceCriterion);
		float rankingFactor = (float) Math.pow(2, (numberOfCriteria - criteriaIndex) + 2);

		WeightBuilder scoreFunction = ScoreFunctionBuilders.weightFactorFunction(rankingFactor);

		return scoreQuery.add(criterionQuery, scoreFunction);
	}

	private FunctionScoreQueryBuilder addRatingScoringFunction(FunctionScoreQueryBuilder scoreQuery)
	{
		FieldValueFactorFunctionBuilder ratingScoreFunction = ScoreFunctionBuilders.fieldValueFactorFunction(BookSearchField.RATING.getFieldName());

		return scoreQuery.add(QueryBuilders.matchAllQuery(), ratingScoreFunction);
	}

	private QueryBuilder getCriterionQuery(BookPreferenceCriterion preferenceCriterion)
	{
		BookSearchField field = preferenceCriterion.getField();

		if (BookSearchField.PUBLICATION_ERA.equals(field))
			return getBoostQueryByEra(PublicationEra.valueOf(preferenceCriterion.getValue()));
		else
			return getBoostQueryByExactText(field.getFieldName(), preferenceCriterion.getValue());
	}

	private QueryBuilder getBoostQueryByEra(PublicationEra publicationEra)
	{
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(BookSearchField.PUBLICATION_ERA.getFieldName());

		if (PublicationEra.MODERN.equals(publicationEra))
			rangeQuery = rangeQuery.gte(PublicationEra.MODERN.getStartYear());
		else
			rangeQuery = rangeQuery.lt(PublicationEra.MODERN.getStartYear());

		return rangeQuery;
	}

	private QueryBuilder getBoostQueryByExactText(String fieldName, String value)
	{
		return QueryBuilders.matchQuery(fieldName, value);
	}
}
