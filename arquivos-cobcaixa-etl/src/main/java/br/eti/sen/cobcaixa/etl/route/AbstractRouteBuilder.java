package br.eti.sen.cobcaixa.etl.route;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractRouteBuilder extends RouteBuilder {

	protected String route;
	protected String failRoute;
	
	// CONSTRUTORES PÚBLICOS
	
	public AbstractRouteBuilder(String dir, int delay) {
		route = configRoute(dir, delay);
		failRoute = configfailRoute(dir);
	}
	
	// MÉTODOS PRIVADOS
	
	private String configRoute(String dir, int delay) {
		StringBuilder rota = new StringBuilder("file:");
		rota.append(dir).append("?noop=true");
		rota.append("&idempotent=true&idempotentKey=File: ${file:absolute.path} - size ${file:size}&idempotentRepository=#jdbcMessageIdRepository");
		rota.append("&delay=");
		rota.append(delay);
		return rota.toString();
	}

	private String configfailRoute(String dir) {
		return new StringBuilder("file:").append(dir).append("/naoprocessado").toString();
	}

}
