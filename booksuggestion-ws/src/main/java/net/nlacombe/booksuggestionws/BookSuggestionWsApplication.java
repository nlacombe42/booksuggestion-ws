package net.nlacombe.booksuggestionws;

import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import net.nlacombe.booksuggestionws.repository.elasticsearch.BookElasticSearchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootApplication
public class BookSuggestionWsApplication implements CommandLineRunner
{
	@Inject
	private BookElasticSearchRepository bookElasticSearchRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(BookSuggestionWsApplication.class);
	}

	@Override
	public void run(String... strings) throws Exception
	{
		List<BookElasticSearch> books = generateBooks(10000);

		bookElasticSearchRepository.save(books);
	}

	private List<BookElasticSearch> generateBooks(int numberOfBooksToGenerate)
	{
		return new Random()
				.ints(numberOfBooksToGenerate, 1, Integer.MAX_VALUE)
				.mapToObj(this::generateBook)
				.collect(Collectors.toList());
	}

	private BookElasticSearch generateBook(int randomNumber)
	{
		BookElasticSearch book = new BookElasticSearch();
		book.setAuthor("Isaac Asimov");
		book.setGenre("Sci-Fi");
		book.setNumberOfPages(randomNumber);

		return book;
	}
}
