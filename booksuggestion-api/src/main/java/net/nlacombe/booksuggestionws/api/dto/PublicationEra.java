package net.nlacombe.booksuggestionws.api.dto;

public enum PublicationEra
{
	CLASSIC(0),
	MODERN(1500);

	private int startYear;

	PublicationEra(int startYear)
	{
		this.startYear = startYear;
	}

	public int getStartYear()
	{
		return startYear;
	}
}
