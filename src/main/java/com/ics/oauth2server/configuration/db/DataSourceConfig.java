package com.ics.oauth2server.configuration.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.ics.oauth2server"
        },
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManagerFactory"
)

@PropertySource("classpath:application.properties")
@EnableTransactionManagement()
@Profile({"default", "prod"})
public class DataSourceConfig {

    private final Environment env;

    public static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }



    @Bean
    @Primary
    public DataSource dataSource() {
        LOGGER.info("Creating datasource bean..");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("datasource.sql-driver"));
        dataSourceBuilder.url(env.getProperty("datasource.url"));
        dataSourceBuilder.username(env.getProperty("datasource.username"));
        dataSourceBuilder.password(env.getProperty("datasource.password"));
        return dataSourceBuilder.build();
    }


    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LOGGER.info("Creating LocalContainerEntityManagerFactory bean..");
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.ics.oauth2server.common"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    @Primary()
    JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        LOGGER.info("Creating JPA Transactional Manager bean..");
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


    final Properties additionalProperties() {
        LOGGER.info("Getting additional hibernate property..");
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        return hibernateProperties;
    }


}

