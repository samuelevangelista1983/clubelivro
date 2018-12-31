package br.eti.sen.cobcaixa.copy;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractRouteBuilder extends RouteBuilder {
	
	protected String srcDirRoute;
	protected String destDirRoute;
	
	public AbstractRouteBuilder(String src, String dest) {
		srcDirRoute = configSrcFileRoute(src);
		destDirRoute = configDestFileRoute(dest);
	}
	
	private String configSrcFileRoute(String dir) {
		StringBuilder rota = new StringBuilder("file:");
		rota.append(dir).append("?noop=true&moveFailed=.error/${file:name.noext}-${date:now:yyyyMMddHHmmssSSS}.${file:name.ext}");
		return rota.toString();
	}
	
	private String configDestFileRoute(String dir) {
		return new StringBuilder("file:").append(dir).toString();
	}

}
