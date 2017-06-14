package net.nlacombe.booksuggestionws.utils;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.data.elasticsearch.BookElasticSearch;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookMapperTest
{
	private BookMapper bookMapper = new BookMapper();

	@Test
	public void maps_elastic_search_book_to_dto_book()
	{
		BookElasticSearch bookElasticSearch = new BookElasticSearch();
		bookElasticSearch.setAuthor("Isaac Asimov");
		bookElasticSearch.setGenre("Sci-Fi");
		bookElasticSearch.setRating(4.5f);
		bookElasticSearch.setYearOfPublication(1952);
		bookElasticSearch.setNumberOfPages(1000);

		Book actualBook = bookMapper.toBook(bookElasticSearch);

		assertThat(actualBook).isNotNull();
		assertThat(actualBook.getAuthor()).isEqualTo(bookElasticSearch.getAuthor());
		assertThat(actualBook.getGenre()).isEqualTo(bookElasticSearch.getGenre());
		assertThat(actualBook.getRating()).isEqualTo(bookElasticSearch.getRating());
		assertThat(actualBook.getYearOfPublication()).isEqualTo(bookElasticSearch.getYearOfPublication());
		assertThat(actualBook.getNumberOfPages()).isEqualTo(bookElasticSearch.getNumberOfPages());
	}

	@Test
	public void maps_dto_book_to_elastic_search_book()
	{
		Book book = new Book();
		book.setAuthor("Isaac Asimov");
		book.setGenre("Sci-Fi");
		book.setRating(4.5f);
		book.setYearOfPublication(1952);
		book.setNumberOfPages(1000);

		BookElasticSearch actualBook = bookMapper.toBookElasticSearch(book);

		assertThat(actualBook).isNotNull();
		assertThat(actualBook.getAuthor()).isEqualTo(book.getAuthor());
		assertThat(actualBook.getGenre()).isEqualTo(book.getGenre());
		assertThat(actualBook.getRating()).isEqualTo(book.getRating());
		assertThat(actualBook.getYearOfPublication()).isEqualTo(book.getYearOfPublication());
		assertThat(actualBook.getNumberOfPages()).isEqualTo(book.getNumberOfPages());
	}
}