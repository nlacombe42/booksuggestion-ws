package net.nlacombe.booksuggestionws.api.dto;

import java.util.List;

public class BookSuggestionRequest
{
	private PageRequest pageRequest;
	private List<BookPreferenceCriterion> orderedPreferenceCriteria;

	public PageRequest getPageRequest()
	{
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest)
	{
		this.pageRequest = pageRequest;
	}

	public List<BookPreferenceCriterion> getOrderedPreferenceCriteria()
	{
		return orderedPreferenceCriteria;
	}

	public void setOrderedPreferenceCriteria(List<BookPreferenceCriterion> orderedPreferenceCriteria)
	{
		this.orderedPreferenceCriteria = orderedPreferenceCriteria;
	}
}
