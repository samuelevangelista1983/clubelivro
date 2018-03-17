package br.eti.sen.cobcaixa.copy;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractRouteBuilder extends RouteBuilder {
	
	protected String srcDirRoute;
	protected String destDirRoute;
	
	public AbstractRouteBuilder(String src, String dest, int delay) {
		srcDirRoute = configSrcFileRoute(src, delay);
		destDirRoute = configDestFileRoute(dest);
	}
	
	private String configSrcFileRoute(String dir, int delay) {
		StringBuilder rota = new StringBuilder("file:");
		rota.append(dir).append("?noop=true");
		rota.append("&delay=");
		rota.append(delay);
		return rota.toString();
	}
	
	private String configDestFileRoute(String dir) {
		return new StringBuilder("file:").append(dir).toString();
	}

}
