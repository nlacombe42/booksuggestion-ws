package net.nlacombe.booksuggestionws.service.impl;

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
	private BookElasticSearchService bookElasticSearchService;

	@Test
	public void delete_all_calls_elastic_search_service()
	{
		bookService.deleteAllBooks();

		verify(bookElasticSearchService).deleteAllBooks();
	}
}