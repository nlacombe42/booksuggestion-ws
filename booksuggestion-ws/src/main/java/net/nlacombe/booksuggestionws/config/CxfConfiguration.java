package net.nlacombe.booksuggestionws.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.nlacombe.booksuggestionws.wsutil.CorsFilter;
import net.nlacombe.booksuggestionws.wsutil.RequestLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfiguration
{
	@Bean
	public RequestLogFilter requestLogFilter()
	{
		return new RequestLogFilter();
	}

	@Bean
	public CorsFilter corsFilter()
	{
		return new CorsFilter();
	}

	@Bean
	public JacksonJsonProvider jacksonJsonProvider()
	{
		return new JacksonJsonProvider();
	}
}
