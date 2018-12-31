package br.eti.sen.cobcaixa.copy;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogCopyProcessor implements Processor {

	private static Logger LOGGER = LoggerFactory.getLogger(LogCopyProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		LOGGER.info(String.format("Copiando o arquivo %s", in.getHeader("CamelFilePath")));
	}

}
