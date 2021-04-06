/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus;

import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

/**
 * Contains Application configuration including config needed for spring data jdbc.
 * @author James Amoore
 */
@Configuration
@EnableJdbcRepositories
public class AppConfig extends AbstractJdbcConfiguration
{
	@Bean
	public IWebsiteChecker websiteChecker()
	{
		return new WebsiteStatusChecker();
	}
	
	@Bean
	public DataSource dataSource()
			  throws Exception
	{
		String host = System.getProperty("db.host", "localhost");
		int port = Integer.parseInt(System.getProperty("db.port","3306"));
		String dbName = System.getProperty("db.database","telegram_bot");
		
		MariaDbDataSource dataSource = new MariaDbDataSource(host, port, dbName);
		dataSource.setUser(System.getProperty("db.user"));
		dataSource.setPassword(System.getProperty("db.password"));
		
		return dataSource;
	}

	@Bean
	NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource)
	{
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	TransactionManager transactionManager(DataSource dataSource)
	{
		return new DataSourceTransactionManager(dataSource);
	}
}
