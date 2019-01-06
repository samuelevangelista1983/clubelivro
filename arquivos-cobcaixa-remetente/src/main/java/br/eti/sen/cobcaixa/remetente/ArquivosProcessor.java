package br.eti.sen.cobcaixa.remetente;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArquivosProcessor implements Processor {

	private static Logger LOGGER = LoggerFactory.getLogger(ArquivosProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		LOGGER.info(String.format("Lendo o arquivo %s", in.getHeader("CamelFilePath")));
		File arquivo = in.getBody(File.class);
		
		if (!arquivo.canRead()) {
			throw new IOException(String.format("No momento não é permitido a leitura do arquivo %s", in.getHeader("CamelFilePath")));
		}
		
		String conteudo = new String(Files.readAllBytes(arquivo.toPath()));
		exchange.getOut().setBody(conteudo);
	}

}
