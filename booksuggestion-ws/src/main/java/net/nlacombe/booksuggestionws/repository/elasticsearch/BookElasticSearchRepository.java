package net.nlacombe.booksuggestionws.repository.elasticsearch;

import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookElasticSearchRepository extends ElasticsearchRepository<BookElasticSearch, String>
{
	void deleteByBookId(int bookId);
}
