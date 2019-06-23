package crvnluz.cobcaixa.config.json;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class JsonConfig {

	@Bean
	@Primary
	public Gson getGson() {
		return new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
	}
}
