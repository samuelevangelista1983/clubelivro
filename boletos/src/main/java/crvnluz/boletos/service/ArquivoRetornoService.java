package crvnluz.boletos.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crvnluz.boletos.entidade.Boleto;
import crvnluz.boletos.repositorio.BoletoRepositorio;

@Service
public class ArquivoRetornoService {
	
	private final Logger logger = LoggerFactory.getLogger(ArquivoRetornoService.class);
	
	@Autowired
	private BoletoRepositorio boletoRepositorio;

	private Boleto preencherDadosSegmentoT(String linha) {
		String numeroBeneficiario = linha.substring(58, 69);
		Boleto boleto = null;
		
		if (!numeroBeneficiario.trim().isEmpty()) {
			boleto = boletoRepositorio.findByNumeroBeneficiario(numeroBeneficiario);
			
			if (boleto == null) {
				throw new ArquivoRetornoException(String.format("Não foi encontrado o boleto de número %s na base de dados", numeroBeneficiario));
			}
			
			String codigoRetorno = linha.substring(15, 17);
			
			if ("06".equals(codigoRetorno)) {
				boleto.setSituacao(1);
				
			} else if ("09".equals(codigoRetorno)) {
				boleto.setSituacao(3);
				
			} else {
				throw new ArquivoRetornoException(String.format("Código de retorno %s desconhecido", codigoRetorno));
			}
			
			if (boleto.getSituacao() != 5) {
				boleto.setValorTarifa(linha.substring(198, 213));
			}
			
		} else {
			logger.warn("O número do beneficiário no arquivo de retorno estava vazio");
		}
		
		return boleto;
	}
	
	private void preencherDadosSegmentoU(Boleto boleto, String linha) {
		if (boleto.getSituacao().equals(1)) {
			boleto.setValorPago(linha.substring(77, 92));
			boleto.setValorCreditado(linha.substring(92, 107));
			boleto.setPgto(linha.substring(137, 145));
			boleto.setEfetivacaoCredito(linha.substring(145, 153));
		}
	}
	
	// MÉTODOS PÚBLICOS
	
	public void processarArquivo(String arquivo) {
		logger.info("Processando arquivo de retorno");
		String[] linhas = arquivo.split("\n");
		List<Boleto> boletos = new ArrayList<>();
		Boleto boleto = null;
		
		for (String linha: linhas) {
			try {
				char tipoRegistro = linha.charAt(7);
				
				if (tipoRegistro == '3') {
					char segmento = linha.charAt(13);
					
					if (segmento == 'T') {
						boleto = null;
						boleto = preencherDadosSegmentoT(linha);
						
					} else if (boleto != null && segmento == 'U') {
						preencherDadosSegmentoU(boleto, linha);
						boletos.add(boleto);
					}
				}
				
			} catch (ArquivoRetornoException ex) {
				logger.error(ex.getMessage());
				boleto = null;
				
			} catch (Throwable throwable) {
				logger.error("Ocorreu um erro ao processar arquivo de retorno", throwable);
				boleto = null;
			}
		}
		
		for (Boleto item: boletos) {
			try {
				boletoRepositorio.save(item);
				
			} catch (Throwable throwable) {
				logger.error(String.format("Ocorreu um erro ao salvar o boleto de número %s", item.getNumeroBeneficiario()), throwable);
			}
		}
	}
	
}
