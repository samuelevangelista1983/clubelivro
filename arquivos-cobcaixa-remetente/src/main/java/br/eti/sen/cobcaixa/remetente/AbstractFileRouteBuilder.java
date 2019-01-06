package br.eti.sen.cobcaixa.remetente;

import org.apache.camel.builder.RouteBuilder;

public abstract class AbstractFileRouteBuilder extends RouteBuilder {
	
	protected String route;
	protected int redeliveryDelay;
	protected int maximumRedeliveries;
	
	public AbstractFileRouteBuilder(String diretorio, int delay, int redeliveryDelay, int maximumRedeliveries) {
		route = String.format("file:%s?delay=%s", diretorio, delay);
		this.redeliveryDelay = redeliveryDelay;
		this.maximumRedeliveries = maximumRedeliveries;
	}
	
}
