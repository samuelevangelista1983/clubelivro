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


public class ArquivoRemProcessor implements Processor {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ArquivoRemProcessor.class);
	
	// MÉTODOS PRIVADOS
	
	private void preencherDadosSegmentoP(Boleto boleto, String linha) throws ParseException {
		boleto.setNumeroBanco(linha.substring(39, 57).trim());
		boleto.setNossoNumero(linha.substring(62, 73).trim());
		boleto.setVcto(linha.substring(77, 85));
		boleto.setValorNominal(linha.substring(85, 100));
		boleto.setEmissao(linha.substring(109, 117));
	}
	
	private void preencherDadosSegmentoQ(Boleto boleto, String linha) {
		String documento = linha.charAt(17) == '1' ? linha.substring(22, 33) : linha.substring(18, 33);
		boleto.setCpfSacado(documento);
	}
	
	// MÉTODOS PÚBLICOS
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		StringBuilder msg = new StringBuilder("Processando os boletos emitidos no arquivo de remessa ");
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
				
				if (segmento == 'P') {
					if (boleto == null) {
						boleto = new Boleto();
					}
					
					preencherDadosSegmentoP(boleto, linha);
					
				} else if (segmento == 'Q') {
					preencherDadosSegmentoQ(boleto, linha);
					
				} else if (segmento == 'R') {
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
