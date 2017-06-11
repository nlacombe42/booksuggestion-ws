package net.nlacombe.booksuggestionws.api.dto;

public class BookPreferenceCriterion
{
	private BookSearchField field;
	private String value;

	public BookSearchField getField()
	{
		return field;
	}

	public void setField(BookSearchField field)
	{
		this.field = field;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
