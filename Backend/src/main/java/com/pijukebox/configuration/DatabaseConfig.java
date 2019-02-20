package com.pijukebox.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableJpaRepositories("com.pijukebox.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    /**
     * The {@link DataSource} representing the database connection.
     * In our case we're creating an in-memory database using H2,
     * so the setup is simple.
     *
     * @return The connection to the database
     */
    private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/pijukebox?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USER_NAME = "root";
    private final String PASSWORD = "";


    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setUrl(URL);
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setInitialSize(5);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        return adapter;
    }

    @Bean
    @PersistenceContext
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.pijukebox.model");
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}