package top.roothk.service.domaindns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DomainDnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainDnsApplication.class, args);
    }
}
