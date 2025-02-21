package site.snewbie.tix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFrameworkApplication.class, args);
    }
}
