package net.nlacombe.booksuggestionws.api.dto;

public class BookPreferenceCriterion
{
	private BookSearchField field;
	private String value;

	public BookPreferenceCriterion()
	{
	}

	public BookPreferenceCriterion(BookSearchField field, String value)
	{
		this.field = field;
		this.value = value;
	}

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
