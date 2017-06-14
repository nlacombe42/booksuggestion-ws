package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.BookSuggestionWsApplication;
import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookPreferenceCriterion;
import net.nlacombe.booksuggestionws.api.dto.BookSearchField;
import net.nlacombe.booksuggestionws.api.dto.Page;
import net.nlacombe.booksuggestionws.api.dto.PageRequest;
import net.nlacombe.booksuggestionws.api.dto.PublicationEra;
import net.nlacombe.booksuggestionws.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookSuggestionWsApplication.class)
public class BookServiceImplIntegrationTest
{
	private static final String ISAAC_ASIMOV = "Isaac Asimov";
	private static final String ARTHUR_C_CLARKE = "Arthur C. Clarke";
	private static final String SCI_FI = "Sci-Fi";
	private static final String THRILLER = "Thriller";

	@Inject
	private BookService bookService;

	@Test
	public void ranks_by_author_then_genre_then_rating_given_preference_of_author_then_genre()
	{
		Book isaacSciFiRate4 = new Book(1, ISAAC_ASIMOV, SCI_FI, 100, 1951, 4);
		Book isaacSciFiRate3 = new Book(2, ISAAC_ASIMOV, SCI_FI, 100, 1951, 3);
		Book isaacThriller = new Book(3, ISAAC_ASIMOV, THRILLER, 100, 1951, 5);
		Book clarkeSciFi = new Book(4, ARTHUR_C_CLARKE, SCI_FI, 100, 1951, 2);
		BookPreferenceCriterion authorPreference = new BookPreferenceCriterion(BookSearchField.AUTHOR, "Isaac");
		BookPreferenceCriterion genrePreference = new BookPreferenceCriterion(BookSearchField.GENRE, "Sci-Fi");
		List<BookPreferenceCriterion> orderedPreferenceCriteria = Arrays.asList(authorPreference, genrePreference);
		PageRequest pageRequest = new PageRequest(0, 5);

		bookService.deleteAllBooks();
		bookService.create(isaacSciFiRate4);
		bookService.create(isaacSciFiRate3);
		bookService.create(isaacThriller);
		bookService.create(clarkeSciFi);
		Page<Book> bookPage = bookService.getBookSuggestions(pageRequest, orderedPreferenceCriteria);

		assertThat(bookPage).isNotNull();
		assertThat(bookPage.getElements()).containsExactly(isaacSciFiRate4, isaacSciFiRate3, isaacThriller, clarkeSciFi);
	}

	@Test
	public void ranks_by_modern_era()
	{
		Book modernBook = new Book(1, ISAAC_ASIMOV, SCI_FI, 100, 1951, 4);
		Book classicBook = new Book(2, ISAAC_ASIMOV, SCI_FI, 100, 1100, 3);
		BookPreferenceCriterion eraPreference = new BookPreferenceCriterion(BookSearchField.PUBLICATION_ERA, PublicationEra.MODERN.name());
		List<BookPreferenceCriterion> orderedPreferenceCriteria = Collections.singletonList(eraPreference);
		PageRequest pageRequest = new PageRequest(0, 5);

		bookService.deleteAllBooks();
		bookService.create(modernBook);
		bookService.create(classicBook);
		Page<Book> bookPage = bookService.getBookSuggestions(pageRequest, orderedPreferenceCriteria);

		assertThat(bookPage).isNotNull();
		assertThat(bookPage.getElements()).containsExactly(modernBook, classicBook);
	}

	@Test
	public void ranks_by_classic_era()
	{
		Book modernBook = new Book(1, ISAAC_ASIMOV, SCI_FI, 100, 1951, 4);
		Book classicBook = new Book(2, ISAAC_ASIMOV, SCI_FI, 100, 1100, 3);
		BookPreferenceCriterion eraPreference = new BookPreferenceCriterion(BookSearchField.PUBLICATION_ERA, PublicationEra.CLASSIC.name());
		List<BookPreferenceCriterion> orderedPreferenceCriteria = Collections.singletonList(eraPreference);
		PageRequest pageRequest = new PageRequest(0, 5);

		bookService.deleteAllBooks();
		bookService.create(modernBook);
		bookService.create(classicBook);
		Page<Book> bookPage = bookService.getBookSuggestions(pageRequest, orderedPreferenceCriteria);

		assertThat(bookPage).isNotNull();
		assertThat(bookPage.getElements()).containsExactly(classicBook, modernBook);
	}
}