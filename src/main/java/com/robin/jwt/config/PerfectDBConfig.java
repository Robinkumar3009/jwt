//package com.robin.jwt.config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Configuration
//public class PerfectDBConfig {
//    
//    @Autowired
//    Environment env;
//    
//    @Bean
//    public DataSource dataSource()
//    {
//        DataSourceBuilder dataSourceBuilder= DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
//        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
//        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
//        return dataSourceBuilder.build();
//    }
//    
//    @Bean
//    @Autowired
//    public JdbcTemplate jdbc()
//    {
//        JdbcTemplate jdbc=new JdbcTemplate();
//        jdbc.setDataSource(dataSource());
//        return jdbc;
//    }
//
//}