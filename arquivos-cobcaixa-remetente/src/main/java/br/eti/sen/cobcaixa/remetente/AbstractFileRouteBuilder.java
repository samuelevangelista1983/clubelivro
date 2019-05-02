package br.eti.sen.cobcaixa.remetente;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractFileRouteBuilder extends RouteBuilder {
	
	protected String srcRoute;
	protected String destRoute;
	protected int redeliveryDelay;
	protected int maximumRedeliveries;
	
	
	public AbstractFileRouteBuilder(String diretorio, String destRoute, int delay, int redeliveryDelay, int maximumRedeliveries) {
		srcRoute = String.format("file:%s?delay=%s", diretorio, delay);
		this.destRoute = destRoute;
		this.redeliveryDelay = redeliveryDelay;
		this.maximumRedeliveries = maximumRedeliveries;
	}
	
}
