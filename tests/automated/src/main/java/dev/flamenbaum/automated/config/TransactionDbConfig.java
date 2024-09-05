package dev.flamenbaum.automated.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "dev.flamenbaum.automated.transaction",
        entityManagerFactoryRef = "transactionEntityManagerFactory",
        transactionManagerRef = "transactionTransactionManager"
)
public class TransactionDbConfig {

    @Bean(name = "transaction-datasource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource transactionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transactionEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean transactionEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("transaction-datasource") DataSource transactionDS
    ) {
        return builder
                .dataSource(transactionDS)
                .packages("dev.flamenbaum.automated.transaction")
                .persistenceUnit("secondary")
                .build();
    }

    @Bean(name = "transactionTransactionManager")
    public PlatformTransactionManager transactionTransactionManager(
            @Qualifier("transactionEntityManagerFactory") EntityManagerFactory transactionEntityManagerFactory) {
        return new JpaTransactionManager(transactionEntityManagerFactory);
    }
}
