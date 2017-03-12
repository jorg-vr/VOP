package main;

import dao.interfaces.DAOProvider;
import dao.test.TestDAOProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
public class BackendApplication {

	public static final DAOProvider PROVIDER = TestDAOProvider.getInstance();

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
