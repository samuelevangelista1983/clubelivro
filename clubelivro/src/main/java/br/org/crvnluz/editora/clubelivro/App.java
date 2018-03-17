package br.org.crvnluz.editora.clubelivro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EntityScan(basePackages = {"br.org.crvnluz.editora.clubelivro.*", "org.springframework.data.jpa.convert.threeten"})
@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
}
