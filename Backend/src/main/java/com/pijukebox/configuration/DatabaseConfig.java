package com.pijukebox.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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
import java.util.Properties;

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
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    /**
     * JDBC by itself is tough to use, so we wrap it in the {@link JdbcTemplate} from Spring,
     * which handles a lot of the boilerplate for us.
     * Pure JDBC has its uses though. While it gives a lot of boilerplate,
     * it also gives a lot of control, which can be useful for certain applications.
     * Think of having to make very complex queries for very specific situations.
     *
     * @param dataSource The data source
     * @return the a {@link JdbcTemplate} template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        try {
            return new JdbcTemplate(dataSource);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        try {
            return new PersistenceExceptionTranslationPostProcessor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        try {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setDatabase(Database.MYSQL);
            return adapter;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Bean
    @PersistenceContext
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        try {
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource);
            entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
            entityManagerFactoryBean.setPackagesToScan("com.pijukebox.model");
            return entityManagerFactoryBean;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        try {
            LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
            sfb.setDataSource(dataSource);
            sfb.setPackagesToScan("com.pijukebox.model");
            Properties props = new Properties();
            props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            sfb.setHibernateProperties(props);
            return sfb;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        try {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            return transactionManager;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}