package net.nlacombe.booksuggestionws.api.dto;

public class PageRequest
{
	private long pageNumber;
	private long elementsPerPage;

	public PageRequest()
	{
	}

	public PageRequest(long pageNumber, long elementsPerPage)
	{
		this.pageNumber = pageNumber;
		this.elementsPerPage = elementsPerPage;
	}

	public long getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public long getElementsPerPage()
	{
		return elementsPerPage;
	}

	public void setElementsPerPage(int elementsPerPage)
	{
		this.elementsPerPage = elementsPerPage;
	}
}
