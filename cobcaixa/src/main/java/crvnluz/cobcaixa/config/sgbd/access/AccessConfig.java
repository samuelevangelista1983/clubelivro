package crvnluz.cobcaixa.config.sgbd.access;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages = "crvnluz.cobcaixa.repositorio.access",
		entityManagerFactoryRef = "accessEntityManager",
		transactionManagerRef = "accessTransactionManager")
public class AccessConfig {
	
	@Autowired
	private Environment env;
	
	@Primary
	@Bean
	public DataSource accessDataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(env.getProperty("access.datasource.driver-classname"));
		datasource.setUrl(env.getProperty("access.datasource.url"));
		datasource.setUsername(env.getProperty("access.datasource.username"));
		datasource.setPassword(env.getProperty("access.datasource.password"));
		return datasource;
	}
	
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean accessEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(accessDataSource());
		em.setPackagesToScan("crvnluz.cobcaixa.entidade.access");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.show_sql", "false");
		props.put("hibernate.globally_quoted_identifiers", "true");
		props.put("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
		props.put("hibernate.dialect", "net.ucanaccess.hibernate.dialect.UCanAccessDialect");
		em.setJpaPropertyMap(props);
		return em;
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager accessTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(accessEntityManager().getObject());
		return transactionManager;
	}
}
