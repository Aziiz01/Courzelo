package tn.esprit.pidev.repositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tn.esprit.pidev.PidevApplication;

@EnableScheduling
@SpringBootApplication
public interface IExempleRepository {

    public static void main(String[] args) {
        SpringApplication.run(PidevApplication.class, args);
    }
}
