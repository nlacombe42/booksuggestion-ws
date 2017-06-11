package net.nlacombe.booksuggestionws.api.dto;

public class Book
{
	private String author;
	private String genre;
	private int numberOfPages;
	private int yearOfPublication;
	private float rating;

	public Book(String author, String genre, int numberOfPages, int yearOfPublication, float rating)
	{
		this.author = author;
		this.genre = genre;
		this.numberOfPages = numberOfPages;
		this.yearOfPublication = yearOfPublication;
		this.rating = rating;
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
