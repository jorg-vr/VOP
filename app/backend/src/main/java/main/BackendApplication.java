package main;

import dao.database.ProductionProvider;
import dao.interfaces.DAO;
import dao.interfaces.DAOProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PreDestroy;


@SpringBootApplication
@ComponentScan(basePackages = {"spring"})
public class BackendApplication {

	public static void main(String[] args) throws Exception {

        if (args.length == 1) {
            ProductionProvider.initializeProvider(args[0]);
        } else if (args.length == 2) {
            ProductionProvider.initializeProvider(args[1]);
        }
        else {
            System.err.println("Wrong number of arguments");
            return;
        }
        // PROVIDER = ProductionProvider.getInstance(); would be nice to have this, but doesn't work for tests
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                super.addCorsMappings(registry);
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
            }
        };
	}

    public static DAOProvider getProvider() {
        return ProductionProvider.getInstance();
    }

	@PreDestroy
    public void preDestroy() {
        ProductionProvider.getInstance().close();
    }
}
