package crvnluz.boletos.job;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import crvnluz.boletos.entidade.Boleto;
import crvnluz.boletos.entidade.BoletoPendente;
import crvnluz.boletos.entidade.Sacado;
import crvnluz.boletos.repositorio.BoletoPendenteRepositorio;
import crvnluz.boletos.service.BoletoService;

@EnableScheduling
@Component
public class Processador {
	
	private final Logger logger = LoggerFactory.getLogger(Processador.class);
	
	@Autowired
	private BoletoPendenteRepositorio pendenteRepositorio;
	private Pageable limite;
	@Autowired
	private BoletoService boletoService;
	private ObjectMapper objectMapper;
	
	public Processador() {
		limite = PageRequest.of(0, 1000);
		objectMapper = new ObjectMapper();
	}
	
	private Boleto getBoleto(String conteudoJson) throws IOException {
		Boleto boleto = new Boleto();
		JsonNode json = objectMapper.readTree(conteudoJson);
		boleto.setSituacao(json.get("situacao").asInt());
		boleto.setEmissao(json.get("emissao").asText());
		boleto.setValorNominal(json.get("valorNominal").decimalValue());
		boleto.setVcto(json.get("vcto").asText());
		boleto.setNumeroBanco(json.get("numeroBanco").asText());
		boleto.setNumeroBeneficiario(json.get("numeroBeneficiario").asText());
		
		if (json.has("pgto")) {
			boleto.setPgto(json.get("pgto").asText());
		}
		
		if (json.has("valorPago")) {
			boleto.setValorPago(json.get("valorPago").decimalValue());
		}
		
		if (json.has("valorTarifa")) {
			boleto.setValorTarifa(json.get("valorTarifa").decimalValue());
		}
		
		if (json.has("valorCreditado")) {
			boleto.setValorCreditado(json.get("valorCreditado").decimalValue());
		}
		
		if (json.has("efetivacaoCredito")) {
			boleto.setEfetivacaoCredito(json.get("efetivacaoCredito").asText());
		}
		
		if (json.has("mensagemFichaCompensacao")) {
			boleto.setMensagemFichaCompensacao(json.get("mensagemFichaCompensacao").asText());
		}
		
		if (json.has("mensagemReciboSacado")) {
			boleto.setMensagemReciboSacado(json.get("mensagemReciboSacado").asText());
		}
		
		Sacado sacado = new Sacado();
		JsonNode node = json.get("sacado");
		sacado.setNome(node.get("nome").asText());
		sacado.setDocumento(node.get("documento").asText());
		sacado.setCep(node.get("cep").asText());
		sacado.setLogradouro(node.get("logradouro").asText());
		sacado.setBairro(node.get("bairro").asText());
		sacado.setCidade(node.get("cidade").asText());
		sacado.setUf(node.get("uf").asText());
		
		if (node.has("numero")) {
			sacado.setNumero(node.get("numero").asText());
		}
		
		if (node.has("complemento")) {
			sacado.setComplemento(node.get("complemento").asText());
		}
		
		boleto.setSacado(sacado);
		return boleto;
	}
	
	// Espera 30 minutos antes da primeira execução e as demais ocorrerão à cada 30 minutos
	@Scheduled(initialDelay = 1800000, fixedDelay = 1800000)
	//@Scheduled(fixedDelay = 10000)
	@Transactional
	public void processar() {
		Page<BoletoPendente> page = pendenteRepositorio.findAll(limite);
		List<BoletoPendente> pendentes = page.getContent();
		
		for (BoletoPendente pendente: pendentes) {
			try {
				Boleto boleto = getBoleto(pendente.getJson());
				boletoService.processar(boleto);
				
			} catch (Throwable throwable) {
				logger.error("Falha ao processar boleto", throwable);
			}
		}
		
		if (!pendentes.isEmpty()) {
			pendenteRepositorio.deleteInBatch(pendentes);
		}
	}
	
}
