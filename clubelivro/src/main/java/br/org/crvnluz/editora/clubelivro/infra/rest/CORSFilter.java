package br.org.crvnluz.editora.clubelivro.infra.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSFilter {

	/*
	 * @Bean public FilterRegistrationBean corsFilter() { CorsConfiguration config =
	 * new CorsConfiguration().applyPermitDefaultValues();
	 * config.addAllowedMethod(HttpMethod.GET);
	 * config.addAllowedMethod(HttpMethod.DELETE);
	 * config.addAllowedMethod(HttpMethod.POST);
	 * config.addAllowedMethod(HttpMethod.PUT); UrlBasedCorsConfigurationSource
	 * source = new UrlBasedCorsConfigurationSource();
	 * source.registerCorsConfiguration("/**", config); FilterRegistrationBean bean
	 * = new FilterRegistrationBean(new CorsFilter(source)); bean.setOrder(0);
	 * return bean; }
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
