package net.nlacombe.booksuggestionws.config;

import net.nlacombe.booksuggestionws.constants.DbConstants;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig
{
	@Bean
	public Flyway flyway(javax.sql.DataSource datasource)
	{
		Flyway flyway = new Flyway();
		flyway.setDataSource(datasource);
		flyway.setLocations("classpath:" + DbConstants.DB_MIGRATION_FOLDER);
		flyway.setSqlMigrationPrefix(DbConstants.DB_MIGRATION_FILE_PREFIX);
		flyway.setSqlMigrationSeparator(DbConstants.DB_MIGRATION_FILE_DESCRIPTION_SEPARATOR);
		flyway.migrate();

		return flyway;
	}
}
