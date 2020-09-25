package codingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // TODO: enable security
@EnableJpaRepositories
public class TicTacToe {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToe.class);
    }
}
