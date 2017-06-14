package net.nlacombe.booksuggestionws.api.webservice;

import net.nlacombe.booksuggestionws.api.dto.Book;
import net.nlacombe.booksuggestionws.api.dto.BookSuggestionRequest;
import net.nlacombe.booksuggestionws.api.dto.Page;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BookWebService
{
	@POST
	@Path("/suggestions")
	Page<Book> getBookSuggestions(BookSuggestionRequest bookSuggestionRequest);

	@POST
	Book createBook(Book book);

	@PUT
	@Path("/{bookId}")
	Book updateBook(@PathParam("bookId") int bookId, Book book);

	@DELETE
	@Path("/{bookId}")
	void deleteBook(@PathParam("bookId") int bookId);
}
