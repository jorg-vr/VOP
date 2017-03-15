package main;

import dao.database.ProductionProvider;
import dao.interfaces.DAOProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;


@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
public class BackendApplication {

	public static DAOProvider PROVIDER;

	public static void main(String[] args) {

		if (args.length == 1) {
            if (args[0].equals("production")) {
                ProductionProvider.initializeProvider(true);
            } else if (args[0].equals("development")) {
                ProductionProvider.initializeProvider(false);
            }
        } else {
		    return;
        }
        PROVIDER = ProductionProvider.getInstance();
		SpringApplication.run(BackendApplication.class, args);
	}

	@PreDestroy
    public void preDestroy() {
        PROVIDER.close();
    }
}
