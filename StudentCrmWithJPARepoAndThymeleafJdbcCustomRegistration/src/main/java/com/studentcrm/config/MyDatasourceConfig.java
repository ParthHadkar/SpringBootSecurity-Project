package com.studentcrm.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"${spring.data.jpa.repository.packages}"})
public class MyDatasourceConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			DataSource appDataSource) {
		return builder.dataSource(appDataSource).build();
	}
	@Primary
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
 
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
          entityManagerFactory.getObject());
        return transactionManager;
    }

	@Bean
	@ConfigurationProperties(prefix = "security.datasource")
	public DataSource securtityDataSource() {
		return DataSourceBuilder.create().build();
	}

	/*@Bean
	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return  builder.dataSource(securtityDataSource()).build();
	}*/
	
	@Bean
	@ConfigurationProperties(prefix = "spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("securtityDataSource")DataSource securtityDataSource) {
		return builder.dataSource(securtityDataSource).build();
	}

	 @Bean
	    public PlatformTransactionManager securityTransactionManager(@Qualifier("securityEntityManagerFactory")LocalContainerEntityManagerFactoryBean securityEntityManagerFactory) {
	 
	        JpaTransactionManager transactionManager
	          = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(
	        		securityEntityManagerFactory.getObject());
	        return transactionManager;
	    }
}
