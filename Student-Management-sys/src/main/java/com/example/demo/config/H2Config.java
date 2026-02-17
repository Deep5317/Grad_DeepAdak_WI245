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
    basePackages = "com.example.demo.dao.h2",
    entityManagerFactoryRef = "h2EntityManager",
    transactionManagerRef = "h2TransactionManager"
)
public class H2Config {

	@Bean
	public DataSource h2DataSource() {

	    HikariDataSource ds = new HikariDataSource();
	    ds.setJdbcUrl("jdbc:h2:mem:deep");
	    ds.setUsername("sa");
	    ds.setPassword("");
	    ds.setDriverClassName("org.h2.Driver");

	    return ds;
	}


    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean h2EntityManager() {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(h2DataSource());
        emf.setPackagesToScan("com.example.demo.entities");
        emf.setPersistenceUnitName("h2");

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.hbm2ddl.auto", "update");

        emf.setJpaPropertyMap(props);

        return emf;
    }

    @Primary
    @Bean
    public PlatformTransactionManager h2TransactionManager(
            @Qualifier("h2EntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
