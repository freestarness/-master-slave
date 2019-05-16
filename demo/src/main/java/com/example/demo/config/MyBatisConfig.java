package com.example.demo.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Configuration
@AutoConfigureAfter({DataSourceConfig.class})
public class MyBatisConfig {


    @Autowired()@Qualifier("demo1DataSource")
    private DataSource demo1DataSource;
    @Autowired()@Qualifier("demo2DataSource")
    private DataSource demo2DataSource;


    @Bean(name = "multiDataSource")
    public DynamicDataSource  routeDataSourceProxy(){
        DynamicDataSource proxy = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("demo1DataSource",demo1DataSource);
        targetDataSources.put("demo2DataSource",demo2DataSource);
        proxy.setDefaultTargetDataSource(demo1DataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(routeDataSourceProxy());
        return manager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(routeDataSourceProxy());
        Resource[] resource1 = new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/*.xml");
        Resource[] resource2 = new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap2/*.xml");
        int resource1Length = resource1.length;
        int resource2length = resource2.length;
        resource1 = Arrays.copyOf(resource1, resource1Length+resource2length);
        System.arraycopy(resource2, 0, resource1, resource1Length, resource2length);
        sessionFactory.setMapperLocations(resource1);
        sessionFactory.setTypeAliasesPackage("com.example.demo.model.demo1,com.example.demo.model.demo2");
        return sessionFactory;
    }

}
