package net.nlacombe.booksuggestionws.api.dto;

import java.util.List;

public class BookSuggestionRequest
{
	private List<BookPreferenceCriterion> orderedPreferenceCriteria;

	public List<BookPreferenceCriterion> getOrderedPreferenceCriteria()
	{
		return orderedPreferenceCriteria;
	}

	public void setOrderedPreferenceCriteria(List<BookPreferenceCriterion> orderedPreferenceCriteria)
	{
		this.orderedPreferenceCriteria = orderedPreferenceCriteria;
	}
}
