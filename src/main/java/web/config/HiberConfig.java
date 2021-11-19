package web.config;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("web")
@PropertySource("classpath:db.properties")
public class HiberConfig {
        private final Environment env;

        @Autowired
        public HiberConfig(Environment env) {
            this.env = env;
        }

        @Bean
        public DataSource getDataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
            dataSource.setUrl(env.getRequiredProperty("db.url"));
            dataSource.setUsername(env.getRequiredProperty("db.username"));
            dataSource.setPassword(env.getRequiredProperty("db.password"));

            return dataSource;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(getDataSource());
            em.setPackagesToScan("db.packageToScan");
            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Properties properties = new Properties();
            properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
            properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
            properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));

            em.setJpaProperties(properties);

            return em;
        }
        @Bean
        public PlatformTransactionManager transactionManager() {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setEntityManagerFactory(getEntityManagerFactoryBean().getObject());
            return jpaTransactionManager;
        }
    }