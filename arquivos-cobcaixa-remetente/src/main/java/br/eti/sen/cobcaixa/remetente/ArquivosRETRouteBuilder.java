package br.eti.sen.cobcaixa.remetente;

import org.apache.camel.LoggingLevel;

public class ArquivosRETRouteBuilder extends AbstractFileRouteBuilder {

	public ArquivosRETRouteBuilder(String diretorio, int delay, int redeliveryDelay, int maximumRedeliveries) {
		super(diretorio, delay, redeliveryDelay, maximumRedeliveries);
	}

	@Override
	public void configure() throws Exception {
		onException(Throwable.class)
			.retryAttemptedLogLevel(LoggingLevel.WARN)
			.redeliveryDelay(redeliveryDelay)
			.maximumRedeliveries(maximumRedeliveries)
			.onExceptionOccurred(new LogExceptionProcessor());
	
		from(route)
			.process(new ArquivosProcessor())
			.to("jms:topic:crvn.cobcaixa.arquivos.ret");

	}

}
