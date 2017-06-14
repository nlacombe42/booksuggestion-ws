package net.nlacombe.booksuggestionws.api.dto;

public class Book
{
	private String author;
	private String genre;
	private int numberOfPages;
	private int yearOfPublication;
	private float rating;

	public Book()
	{
	}

	public Book(String author, String genre, int numberOfPages, int yearOfPublication, float rating)
	{
		this.author = author;
		this.genre = genre;
		this.numberOfPages = numberOfPages;
		this.yearOfPublication = yearOfPublication;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Book book = (Book) o;

		if (numberOfPages != book.numberOfPages) return false;
		if (yearOfPublication != book.yearOfPublication) return false;
		if (Float.compare(book.rating, rating) != 0) return false;
		if (author != null ? !author.equals(book.author) : book.author != null) return false;
		return genre != null ? genre.equals(book.genre) : book.genre == null;
	}

	@Override
	public int hashCode()
	{
		int result = author != null ? author.hashCode() : 0;
		result = 31 * result + (genre != null ? genre.hashCode() : 0);
		result = 31 * result + numberOfPages;
		result = 31 * result + yearOfPublication;
		result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "Book{" +
				"author='" + author + '\'' +
				", genre='" + genre + '\'' +
				", numberOfPages=" + numberOfPages +
				", yearOfPublication=" + yearOfPublication +
				", rating=" + rating +
				'}';
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
