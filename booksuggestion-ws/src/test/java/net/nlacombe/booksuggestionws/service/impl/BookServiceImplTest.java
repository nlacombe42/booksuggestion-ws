package net.nlacombe.booksuggestionws.service.impl;

import net.nlacombe.booksuggestionws.repository.jpa.BookRepository;
import net.nlacombe.booksuggestionws.service.BookElasticSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest
{
	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookElasticSearchService bookElasticSearchService;

	@Test
	public void delete_all_deletes_in_db_and_elastic_search()
	{
		bookService.deleteAllBooks();

		verify(bookRepository).deleteAll();
		verify(bookElasticSearchService).deleteAllBooks();
	}
}