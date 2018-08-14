package pro.savichev.db

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import pro.savichev.Config
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(basePackages = ["pro.savichev.db"])
open class DatabaseConfiguration {
    @Bean
    open fun  dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver")
        dataSource.url = "jdbc:mariadb://" + Config.DB_HOST + ":" + Config.DB_PORT + "/" + Config.DB_NAME
        val properties = Properties()
        properties.setProperty("user", Config.DB_USERNAME)
        properties.setProperty("password", Config.DB_PASSWORD)
        properties.setProperty("useUnicode", "true")
        properties.setProperty("characterEncoding", "utf8")
        properties.setProperty("character_set_server", "utf8mb4")
        properties.setProperty("useJDBCCompliantTimezoneShift", "true")
        properties.setProperty("useLegacyDatetimeCode", "false")
        properties.setProperty("serverTimezone", "UTC")
        properties.setProperty("useSSL", "false")
        dataSource.connectionProperties = properties
        return dataSource
    }

    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan("pro.savichev.db")
        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties())
        return em
    }

    @Bean
    open fun  transactionManager(emf: EntityManagerFactory): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = emf
        return transactionManager
    }

    @Bean
    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    private fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "none")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect")
        return properties
    }

}
