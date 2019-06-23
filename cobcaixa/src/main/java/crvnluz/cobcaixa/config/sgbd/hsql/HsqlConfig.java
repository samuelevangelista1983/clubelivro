package crvnluz.cobcaixa.config.sgbd.hsql;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages = "crvnluz.cobcaixa.repositorio.hsql",
		entityManagerFactoryRef = "hsqlEntityManager",
		transactionManagerRef = "hsqlTransactionManager")
public class HsqlConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public DataSource hsqlDataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(env.getProperty("hsql.datasource.driver-classname"));
		datasource.setUrl(env.getProperty("hsql.datasource.url"));
		datasource.setUsername(env.getProperty("hsql.datasource.username"));
		datasource.setPassword(env.getProperty("hsql.datasource.pas;sword"));
		return datasource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean hsqlEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(hsqlDataSource());
		em.setPackagesToScan("crvnluz.cobcaixa.entidade.hsql");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.show_sql", "false");
		em.setJpaPropertyMap(props);
		return em;
	}
	
	@Bean
	public PlatformTransactionManager hsqlTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(hsqlEntityManager().getObject());
		return transactionManager;
	}
}
