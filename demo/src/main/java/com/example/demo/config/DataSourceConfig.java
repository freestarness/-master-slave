package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver1;
    private RelaxedPropertyResolver propertyResolver2;
    @Override
    public void setEnvironment(Environment env) {
        this.propertyResolver1 = new RelaxedPropertyResolver(env, "spring.datasource1.");
        this.propertyResolver2 = new RelaxedPropertyResolver(env, "spring.datasource2.");
    }
    @Bean(name = "demo1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource1")
    @Primary
    public DataSource demo1DataSource() {
//        logger.info("@@@@@@@@@@@@@@@@注入druid!!!@@@@@@@@@@@@@@@@");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(propertyResolver1.getProperty("url"));
        datasource.setDriverClassName(propertyResolver1.getProperty("driver-class-name"));
        datasource.setUsername(propertyResolver1.getProperty("username"));
        datasource.setPassword(propertyResolver1.getProperty("password"));
        datasource.setInitialSize(Integer.valueOf(propertyResolver1.getProperty("initial-size")));
        datasource.setMinIdle(Integer.valueOf(propertyResolver1.getProperty("min-idle")));
        datasource.setMaxWait(Long.valueOf(propertyResolver1.getProperty("max-wait")));
        datasource.setMaxActive(Integer.valueOf(propertyResolver1.getProperty("max-active")));
        datasource.setValidationQuery(String.valueOf(propertyResolver1.getProperty("validationQuery")));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(propertyResolver1.getProperty("min-evictable-idle-time-millis")));
        try {
            datasource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

    @Bean(name = "demo2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource demo2DataSource() {
//        logger.info("@@@@@@@@@@@@@@@@注入druid!!!@@@@@@@@@@@@@@@@");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(propertyResolver2.getProperty("url"));
        datasource.setDriverClassName(propertyResolver2.getProperty("driver-class-name"));
        datasource.setUsername(propertyResolver2.getProperty("username"));
        datasource.setPassword(propertyResolver2.getProperty("password"));
        datasource.setInitialSize(Integer.valueOf(propertyResolver2.getProperty("initial-size")));
        datasource.setMinIdle(Integer.valueOf(propertyResolver2.getProperty("min-idle")));
        datasource.setMaxWait(Long.valueOf(propertyResolver2.getProperty("max-wait")));
        datasource.setMaxActive(Integer.valueOf(propertyResolver2.getProperty("max-active")));
        datasource.setValidationQuery(String.valueOf(propertyResolver2.getProperty("validationQuery")));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(propertyResolver2.getProperty("min-evictable-idle-time-millis")));
        try {
            datasource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }


}

