package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.demo.dao.pg",
    entityManagerFactoryRef = "pgEntityManager",
    transactionManagerRef = "pgTransactionManager"
)
public class PostgresConfig {

	@Bean
	public DataSource pgDataSource() {

	    HikariDataSource ds = new HikariDataSource();
	    ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
	    ds.setUsername("postgres");
	    ds.setPassword("Deep@1234");
	    ds.setDriverClassName("org.postgresql.Driver");

	    return ds;
	}


    @Bean
    public LocalContainerEntityManagerFactoryBean pgEntityManager() {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(pgDataSource());
        emf.setPackagesToScan("com.example.demo.entities");
        emf.setPersistenceUnitName("pg");

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "update");

        emf.setJpaPropertyMap(props);

        return emf;
    }

    @Bean
    public PlatformTransactionManager pgTransactionManager(
            @Qualifier("pgEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
