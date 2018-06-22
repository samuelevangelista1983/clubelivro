package br.eti.sen.cobcaixa.etl.processor;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.sen.cobcaixa.etl.entidade.Boleto;
import br.eti.sen.utilitarios.texto.StringUtil;


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
		boleto.setNomeSacado(linha.substring(33, 73).trim());
		String endereco = linha.substring(73, 113).trim();
		String[] dadosEndereco = endereco.split(" ");
		StringBuilder logradouro = new StringBuilder();
		StringBuilder complemento = new StringBuilder();
		String numero = null;
		
		for (String dado: dadosEndereco) {
			if (!StringUtil.contemDigito(dado) && numero == null) {
				logradouro.append(dado).append(StringUtil.ESPACO);
				
			} else if (StringUtil.contemDigito(dado) && numero == null) {
				numero = dado;
				
			} else {
				complemento.append(dado).append(StringUtil.ESPACO);
			}
		}
		
		boleto.setLogradouroSacado(logradouro.toString().trim());
		boleto.setNumeroSacado(numero != null ? numero.trim() : null);
		boleto.setComplementoSacado(!complemento.toString().isEmpty() ? complemento.toString().trim() : null);
		boleto.setBairroSacado(linha.substring(113, 128).trim());
		boleto.setCepSacado(linha.substring(128, 136));
		boleto.setCidadeSacado(linha.substring(136, 151).trim());
		boleto.setUfSacado(linha.substring(151, 153));
	}
	
	private void preencherDadosSegmentoR(Boleto boleto, String linha) {
		String mensagem = linha.substring(99, 140).trim();
		
		if (mensagem.contains("ROMANCE_ESTUDO") || mensagem.contains("romance_estudo")) {
			boleto.setCategoriaSacado(3l);
			
		} else if (mensagem.contains("ALTERNADO") || mensagem.contains("alternado")) {
			boleto.setCategoriaSacado(4l);
			
		} else if (mensagem.contains("ROMANCE") || mensagem.contains("romance")) {
			boleto.setCategoriaSacado(2l);
			
		} else if (mensagem.contains("ESTUDO") || mensagem.contains("estudo")) {
			boleto.setCategoriaSacado(1l);
			
		} else {
			boleto.setCategoriaSacado(1l);
		}
	}
	
	private void preencherDadosSegmentoY(Boleto boleto, String linha) {
		String contato = linha.substring(19, 69).trim();
		String[] dadosContato = contato.split(" ");
		String email = dadosContato[0].toLowerCase();
		
		if (Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", 
				email) && !email.endsWith(".combr")) {
			boleto.setEmailSacado(email);
		}
		
		if (dadosContato.length > 1 && StringUtil.valorNumerico(dadosContato[1])) {
			StringBuilder numero = new StringBuilder("31");
			numero.append(dadosContato[1]);
			
			if (Pattern.matches("[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}", numero.toString())) {
				boleto.setTelefoneSacado(numero.toString());
			}
			
			if (Pattern.matches("[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}", numero.toString())) {
				boleto.setCelularSacado(numero.toString());
			}
		}
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
		boolean pessoaFisica = true;
		int count = 0;
		
		for (String linha: linhas) {
			try {
				count++;
				char tipoRegistro = linha.charAt(7);
				
				if (tipoRegistro == '3') {
					char segmento = linha.charAt(13);
					
					if (segmento == 'P') {
						boleto = new Boleto();
						preencherDadosSegmentoP(boleto, linha);
						
					} else if (segmento == 'Q') {
						if (linha.charAt(17) == '1') {
							pessoaFisica = true;
							preencherDadosSegmentoQ(boleto, linha);
							
						} else {
							pessoaFisica = false;
						}
						
					} else if (segmento == 'R' && pessoaFisica) {
						preencherDadosSegmentoR(boleto, linha);
						
						if (Boleto.verificarAdicaoBoleto(boleto)) {
							boletos.add(boleto);
						}
						
					} else if (segmento == 'Y' && pessoaFisica) {
						preencherDadosSegmentoY(boleto, linha);
					}
				}
				
			} catch (Exception e) {
				System.err.println("Deu erro");
				Message out = exchange.getOut();
				String error = out.getHeader("errorMsg", String.class);
				
				if (error == null) {
					StringBuilder errorMsg = new StringBuilder("Falha ao processar o arquivo de remessa ");
					errorMsg.append(in.getBody(File.class).getName());
					errorMsg.append(" na linha ").append(count);
					out.setHeader("errorMsg", errorMsg.toString());
					System.err.println(errorMsg);
					
				} else {
					StringBuilder errorMsg = new StringBuilder(error);
					errorMsg.append("\nFalha ao processar o arquivo de remessa ");
					errorMsg.append(in.getBody(File.class).getName());
					errorMsg.append(" na linha ").append(count);
					out.setHeader("errorMsg", errorMsg.toString());
					System.err.println(errorMsg);
				}
			}
		}
		
		exchange.getOut().setBody(boletos);
	}

}
