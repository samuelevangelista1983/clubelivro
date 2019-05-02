package br.eti.sen.cobcaixa.remetente;

import org.apache.camel.LoggingLevel;

public class ArquivosRETRouteBuilder extends AbstractFileRouteBuilder {

	public ArquivosRETRouteBuilder(String diretorio, String destRoute, int delay, int redeliveryDelay, int maximumRedeliveries) {
		super(diretorio, destRoute, delay, redeliveryDelay, maximumRedeliveries);
	}

	@Override
	public void configure() throws Exception {
		onException(Throwable.class)
			.retryAttemptedLogLevel(LoggingLevel.WARN)
			.redeliveryDelay(redeliveryDelay)
			.maximumRedeliveries(maximumRedeliveries)
			.onExceptionOccurred(new LogExceptionProcessor());
	
		from(srcRoute)
			.process(new ArquivosProcessor())
			.to(destRoute);

	}

}
