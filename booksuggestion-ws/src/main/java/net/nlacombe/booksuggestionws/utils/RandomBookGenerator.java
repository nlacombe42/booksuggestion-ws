package net.nlacombe.booksuggestionws.utils;

import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RandomBookGenerator
{
	private static final String[] AUTHORS = {"Isaac Asimov", "Arthur C. Clarke", "Stephen King"};
	private static final String[] GENRES = {"Sci-Fi", "Horror", "Thriller"};

	public List<BookElasticSearch> generateBooks(int numberOfBooksToGenerate)
	{
		return IntStream.range(1, numberOfBooksToGenerate)
				.mapToObj(i -> generateBook())
				.collect(Collectors.toList());
	}

	private BookElasticSearch generateBook()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();

		BookElasticSearch book = new BookElasticSearch();
		book.setAuthor(getRandomElement(random, AUTHORS));
		book.setGenre(getRandomElement(random, GENRES));
		book.setNumberOfPages(random.nextInt(1, 2000 + 1));
		book.setYearOfPublication(random.nextInt(1930, 2017 + 1));
		book.setRating(random.nextInt(1, 10) / 2f);

		return book;
	}

	public <T> T getRandomElement(ThreadLocalRandom random, T[] array)
	{
		int index = random.nextInt(array.length);

		return array[index];
	}
}
