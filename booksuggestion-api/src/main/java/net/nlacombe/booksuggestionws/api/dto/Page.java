package net.nlacombe.booksuggestionws.api.dto;

import java.util.List;

public class Page<T>
{
	private PageRequest pageRequest;
	private long totalNumberOfElements;
	private List<T> elements;

	public Page()
	{
	}

	public Page(PageRequest pageRequest, long totalNumberOfElements, List<T> elements)
	{
		this.pageRequest = pageRequest;
		this.totalNumberOfElements = totalNumberOfElements;
		this.elements = elements;
	}

	public PageRequest getPageRequest()
	{
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest)
	{
		this.pageRequest = pageRequest;
	}

	public long getTotalNumberOfElements()
	{
		return totalNumberOfElements;
	}

	public void setTotalNumberOfElements(long totalNumberOfElements)
	{
		this.totalNumberOfElements = totalNumberOfElements;
	}

	public List<T> getElements()
	{
		return elements;
	}

	public void setElements(List<T> elements)
	{
		this.elements = elements;
	}
}
