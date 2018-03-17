package br.eti.sen.cobcaixa.etl.processor;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.sen.cobcaixa.etl.entidade.Boleto;

public class ArquivoRetProcessor implements Processor {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ArquivoRemProcessor.class);
	
	// MÉTODOS PRIVADOS
	
	private void preencherDadosSegmentoT(Boleto boleto, String linha) throws ParseException {
		boleto.setNumeroBanco(linha.substring(39, 57).trim());
		boleto.setVcto(linha.substring(73, 81));
		boleto.setValorNominal(linha.substring(81, 96));
		boleto.setValorTarifa(linha.substring(198, 213));
	}
	
	private void preencherDadosSegmentoU(Boleto boleto, String linha) throws ParseException {
		boleto.setValorPago(linha.substring(77, 92));
		boleto.setValorCreditado(linha.substring(92, 107));
		boleto.setPgto(linha.substring(137, 145));
		boleto.setEfetivacaoCredito(linha.substring(145, 153));
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		StringBuilder msg = new StringBuilder("Processando os boletos recebidos no arquivo de retorno ");
		msg.append(in.getBody(File.class).getName());
		LOGGER.info(msg.toString());
		String content = in.getBody(String.class);
		String[] linhas = content.split("\n");
		List<Boleto> boletos = new ArrayList<>();
		Boleto boleto = null;
		
		for (String linha: linhas) {
			char tipoRegistro = linha.charAt(7);
			
			if (tipoRegistro == '3') {
				char segmento = linha.charAt(13);
				
				if (segmento == 'T') {
					if (boleto == null) {
						boleto = new Boleto();
					}
					
					preencherDadosSegmentoT(boleto, linha);
					
				} else if (segmento == 'U') {
					preencherDadosSegmentoU(boleto, linha);
					boletos.add(boleto);
					boleto = null;
				}
				
			} else if (tipoRegistro == '0') {
				if (boleto == null) {
					boleto = new Boleto();
				}
			}
		}
		
		exchange.getOut().setBody(boletos);
	}

}
