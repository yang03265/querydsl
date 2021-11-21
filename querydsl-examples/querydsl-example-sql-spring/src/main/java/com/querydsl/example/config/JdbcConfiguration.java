package com.querydsl.example.config;
import com.zaxxer.hikari.*;
import com.jolbox.bonecp.BoneCPDataSource;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import com.querydsl.sql.types.DateTimeType;
import com.querydsl.sql.types.LocalDateType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:jdbc.properties"})
public class JdbcConfiguration {

    @Inject Environment env;

    @Bean
    // This is used to define dataSource and used to programmatically configure our own DataSource implementation
    // See https://www.baeldung.com/spring-boot-configure-data-source-programmatic for more information
    public DataSource dataSource() {

        /**HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        ds.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
        ds.setUsername(env.getRequiredProperty("jdbc.user"));
        ds.setPassword(env.getRequiredProperty("jdbc.password"));
        ds.setAutoCommit(false);
        ds.setIsolateInternalQueries(true);
        return ds;**/


        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getRequiredProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.user"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        //dataSource.setMinConnectionsPerPartition(0);
        //dataSource.setMaxConnectionsPerPartition(1);
        //dataSource.setPartitionCount(3);
        //dataSource.setIdleMaxAgeInSeconds(1);
        //dataSource.setPoolStrategy("CACHED");
        return dataSource;

    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = H2Templates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.register(new DateTimeType());
        configuration.register(new LocalDateType());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {
        SpringConnectionProvider provider = new SpringConnectionProvider(dataSource());
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }

}
