package br.eti.sen.cobcaixa.remetente;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExceptionProcessor implements Processor {

	private static Logger LOGGER = LoggerFactory.getLogger(LogExceptionProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		LOGGER.error(String.format("Falha ao remeter o arquivo %s", in.getHeader("CamelFilePath")));
	}

}
