package crvnluz.boletos.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.sen.utilitarios.texto.StringUtil;
import crvnluz.boletos.entidade.Boleto;
import crvnluz.boletos.entidade.Sacado;
import crvnluz.boletos.repositorio.BoletoRepositorio;
import crvnluz.boletos.repositorio.SacadoRepositorio;

@Service
public class ArquivoRemessaService {
	
	private final Logger logger = LoggerFactory.getLogger(ArquivoRemessaService.class);
	
	@Autowired
	private BoletoRepositorio boletoRepositorio;
	@Autowired
	private SacadoRepositorio sacadoRepositorio;
	
	private void preencherDadosSegmentoP(Boleto boleto, String linha) {
		boleto.setNumeroBanco(linha.substring(39, 57).trim());
		boleto.setNumeroBeneficiario(linha.substring(62, 73).trim());
		boleto.setVcto(linha.substring(77, 85));
		boleto.setValorNominal(linha.substring(85, 100));
		boleto.setEmissao(linha.substring(109, 117));
	}
	
	private void preencherDadosSegmentoQ(Boleto boleto, String linha) {
		String documento = linha.charAt(17) == '1' ? linha.substring(22, 33) : linha.substring(18, 33);
		Sacado sacado = sacadoRepositorio.findByDocumento(documento);
		
		if (sacado == null) {
			sacado = new Sacado();
			sacado.setDocumento(documento);
		}
		
		sacado.setNome(linha.substring(33, 73).trim());
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
		
		sacado.setLogradouro(logradouro.toString().trim());
		sacado.setNumero(numero != null ? numero.trim() : null);
		sacado.setComplemento(!complemento.toString().isEmpty() ? complemento.toString().trim() : null);
		sacado.setBairro(linha.substring(113, 128).trim());
		sacado.setCep(linha.substring(128, 136));
		sacado.setCidade(linha.substring(136, 151).trim());
		sacado.setUf(linha.substring(151, 153));
		sacado = sacadoRepositorio.save(sacado);
		boleto.setSacado(sacado);
	}
	
	private void verificarAdicaoBoleto(Boleto boleto) {
		LocalDate marco = LocalDate.of(2018, 3, 1);
		boolean emitidoAposFevereiro = marco.isBefore(boleto.getEmissao());
		boolean podeAdicionar = emitidoAposFevereiro;
		
		if (!emitidoAposFevereiro) {
			boolean vctoAposFevereiro = marco.isBefore(boleto.getVcto());
			podeAdicionar = vctoAposFevereiro;
		}
		
		if (!podeAdicionar) {
			throw new ArquivoRemessaException(String.format("Não é permitido o processamento de boletos com a data de emissão e de vencimento antes de %s de %s ", 
					marco.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")), marco.getYear()));
		}
	}
	
	// MÉTODOS PÚBLICOS
	
	public void processarArquivo(String arquivo) {
		logger.info("Processando arquivo de remessa");
		String[] linhas = arquivo.split("\n");
		List<Boleto> boletos = new ArrayList<>();
		Boleto boleto = null;
		
		for (String linha: linhas) {
			try {
				char tipoRegistro = linha.charAt(7);
				
				if (tipoRegistro == '3') {
					char segmento = linha.charAt(13);
					
					if (segmento == 'P') {
						boleto = new Boleto();
						preencherDadosSegmentoP(boleto, linha);
						
					} else if (segmento == 'Q') {
						preencherDadosSegmentoQ(boleto, linha);
						verificarAdicaoBoleto(boleto);
						boleto.setSituacao(0);
						boletos.add(boleto);
					}
				}
				
			} catch (ArquivoRemessaException ex) {
				logger.error(ex.getMessage());
				boleto = null;
				
			} catch (Throwable throwable) {
				logger.error("Ocorreu um erro ao processar arquivo de remessa", throwable);
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
