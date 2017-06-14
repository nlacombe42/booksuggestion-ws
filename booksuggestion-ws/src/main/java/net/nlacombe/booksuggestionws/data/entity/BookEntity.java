package net.nlacombe.booksuggestionws.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;

	private String author;
	private String genre;
	private int numberOfPages;
	private int yearOfPublication;
	private float rating;

	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public int getNumberOfPages()
	{
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages)
	{
		this.numberOfPages = numberOfPages;
	}

	public int getYearOfPublication()
	{
		return yearOfPublication;
	}

	public void setYearOfPublication(int yearOfPublication)
	{
		this.yearOfPublication = yearOfPublication;
	}

	public float getRating()
	{
		return rating;
	}

	public void setRating(float rating)
	{
		this.rating = rating;
	}
}
