package net.nlacombe.booksuggestionws;

import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.repository.elasticsearch.BookElasticSearchRepository;
import net.nlacombe.booksuggestionws.utils.RandomBookGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.util.List;

@SpringBootApplication
public class BookSuggestionWsApplication implements CommandLineRunner
{
	@Inject
	private BookElasticSearchRepository bookElasticSearchRepository;

	@Inject
	private RandomBookGenerator randomBookGenerator;

	public static void main(String[] args)
	{
		SpringApplication.run(BookSuggestionWsApplication.class);
	}

	@Override
	public void run(String... strings) throws Exception
	{
		List<BookElasticSearch> books = randomBookGenerator.generateBooks(50);

		bookElasticSearchRepository.save(books);
	}
}
