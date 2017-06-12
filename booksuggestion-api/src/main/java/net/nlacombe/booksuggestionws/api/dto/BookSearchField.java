package net.nlacombe.booksuggestionws.api.dto;

public enum BookSearchField
{
	AUTHOR("author"),
	GENRE("genre"),
	NUMBER_OF_PAGES("numberOfPages"),
	PUBLICATION_ERA("yearOfPublication"),
	RATING("rating");

	private String fieldName;

	BookSearchField(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldName()
	{
		return fieldName;
	}
}
