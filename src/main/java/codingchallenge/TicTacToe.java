package codingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // TODO: enable security
public class TicTacToe {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToe.class);
    }
}
