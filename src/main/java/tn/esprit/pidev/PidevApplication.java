package tn.esprit.pidev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy  //activer l'aspect
@EnableScheduling //activer scheduling
public class PidevApplication {

    public static void main(String[] args) {
        SpringApplication.run(PidevApplication.class, args);
    }

}
