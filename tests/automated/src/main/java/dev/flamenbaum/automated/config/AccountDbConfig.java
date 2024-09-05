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
        basePackages = "dev.flamenbaum.automated.account",
        entityManagerFactoryRef = "accountEntityManagerFactory",
        transactionManagerRef = "accountTransactionManager"
)
public class AccountDbConfig {

    @Primary
    @Bean(name = "account-datasource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource accountDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "accountEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean accountEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Qualifier("account-datasource") DataSource accountDS
    ) {
        return builder
                .dataSource(accountDS)
                .packages("dev.flamenbaum.automated.account")
                .persistenceUnit("primary")
                .build();
    }

    @Primary
    @Bean(name = "accountTransactionManager")
    public PlatformTransactionManager accountTransactionManager(
            @Qualifier("accountEntityManagerFactory") EntityManagerFactory accountEntityManagerFactory) {
        return new JpaTransactionManager(accountEntityManagerFactory);
    }
}
