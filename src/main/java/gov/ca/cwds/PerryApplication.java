package gov.ca.cwds;

import gov.ca.cwds.security.jwt.JwtService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAutoConfiguration(exclude = {FlywayAutoConfiguration.class})
@ComponentScan("gov.ca.cwds")
@EntityScan("gov.ca.cwds.data.persistence.auth")
@EnableConfigurationProperties({PerryProperties.class})
public class PerryApplication {
  @Bean
  public RestTemplate client() {
    return new RestTemplate();
  }

  @Bean
  @Autowired
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

  @Bean
  @Autowired
  public JwtService jwtService(PerryProperties perryProperties) {
    return new JwtService(perryProperties.getJwt());
  }

  public static void main(String[] args) {
    SpringApplication.run(PerryApplication.class, args);
  }
}
