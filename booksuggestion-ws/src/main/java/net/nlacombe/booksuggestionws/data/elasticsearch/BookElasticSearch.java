package net.nlacombe.booksuggestionws.data.elasticsearch;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.constants.ElasticSearchConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = ElasticSearchConstants.BOOK_SUGGESTION_INDEX_NAME, type = ElasticSearchConstants.BOOKS_TYPE_NAME)
public class BookElasticSearch extends Book
{
	@Id
	private String id;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
