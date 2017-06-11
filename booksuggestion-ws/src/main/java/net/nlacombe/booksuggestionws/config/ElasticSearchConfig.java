package net.nlacombe.booksuggestionws.config;

import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "net.nlacombe.booksuggestionws.repository.elasticsearch")
public class ElasticSearchConfig
{
	@Value("${elasticsearch.host}")
	private String elasticSearchHost;

	@Value("${elasticsearch.port}")
	private int elasticSearchPort;

	@Value("${elasticsearch.clustername}")
	private String elasticSearchClusterName;

	@Bean
	public ElasticsearchOperations elasticsearchTemplate()
	{
		return new ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node().client());
	}
}
