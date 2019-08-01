package br.org.crvnluz.editora.clubelivro.integracao.job;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
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

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Boleto;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.integracao.entidade.IntegrantePendente;
import br.org.crvnluz.editora.clubelivro.integracao.entidade.IntegranteProcessado;
import br.org.crvnluz.editora.clubelivro.integracao.repositorio.IntegrantePendenteRepositorio;
import br.org.crvnluz.editora.clubelivro.integracao.repositorio.IntegranteProcesadoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.BoletoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.IntegranteRepositorio;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.CategoriaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaEntregaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaPgtoService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FrequenciaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.TipoContatoService;

@EnableScheduling
@Component
public class IntegrantePendenteJob {
	
	private final Logger logger = LoggerFactory.getLogger(IntegrantePendenteJob.class);
	
	@Autowired
	private IntegrantePendenteRepositorio pendenteRepositorio;
	@Autowired
	private IntegranteProcesadoRepositorio processadoRepositorio;
	private Pageable limite;
	@Autowired
	private IntegranteRepositorio repositorio;
	@Autowired
	private BoletoRepositorio boletoRepositorio;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private FormaEntregaService formaEntregaService;
	@Autowired
	private FormaPgtoService formaPgtoService;
	@Autowired
	private FrequenciaService frequenciaService;
	@Autowired
	private TipoContatoService tipoContatoService;
	private ObjectMapper objectMapper;
	private FormaEntrega formaEntrega;
	private FormaPgto formaPgto;
	private Frequencia frequencia;
	
	public IntegrantePendenteJob() {
		limite = PageRequest.of(0, 150);
	}
	
	private Boleto getBoleto(Integrante integrante, String conteudoJson) throws IOException {
		Boleto boleto = new Boleto();
		boleto.setIntegrante(integrante);
		JsonNode json = objectMapper.readTree(conteudoJson);
		boleto.setEmissao(json.get("emissao").asText());
		boleto.setNossoNumero(json.get("numeroBeneficiario").asText());
		boleto.setNumeroBanco(json.get("numeroBanco").asText());
		boleto.setValor(json.get("valorNominal").decimalValue());
		boleto.setVcto(json.get("vcto").asText());
		
		if (json.has("pgto")) {
			boleto.setPgto(json.get("pgto").asText());
		}
		
		if (json.has("valorPago")) {
			boleto.setValorPago(json.get("valorPago").decimalValue());
		}
		
		return boleto;
	}
	
	private Integrante getIntegrante(String conteudoJson) throws IOException {
		JsonNode json = objectMapper.readTree(conteudoJson);
		JsonNode node = json.get("sacado");
		String cpf = node.get("documento").asText();
		Integrante integrante = new Integrante();
		integrante.setAtivo(true);
		integrante.setCadastro(LocalDate.now());
		integrante.setCpf(cpf);
		integrante.setFormaEntrega(formaEntrega);
		integrante.setFormaPgtoPref(formaPgto);
		integrante.setFrequencia(frequencia);
		integrante.setDiaVctoPreferencial(20);
		integrante.setNome(node.get("nome").asText());
		Endereco endereco = new Endereco();
		endereco.setCobranca(true);
		endereco.setEntrega(true);
		endereco.setCep(node.get("cep").asText());
		endereco.setLogradouro(node.get("logradouro").asText());
		endereco.setBairro(node.get("bairro").asText());
		endereco.setCidade(node.get("cidade").asText());
		endereco.setUf(node.get("uf").asText());
		
		if (node.has("numero")) {
			endereco.setNumero(node.get("numero").asText());
		}
		
		if (node.has("complemento")) {
			endereco.setComplemento(node.get("complemento").asText());
		}
		
		integrante.adicionarEndereco(endereco);
		
		if (node.has("email")) {
			String email = node.get("email").asText();
			String mailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			
			if (Pattern.matches(mailPattern, email) && !email.contains("combr")) {
				TipoContato tipo = tipoContatoService.pesquisarPorNome("e-mail");
				integrante.adicionarContato(new Contato(tipo, email));
			}
		}
		
		if (node.has("telefone")) {
			String numero = node.get("telefone").asText();
			
			if (Pattern.matches("[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}", numero)) {
				TipoContato tipo = tipoContatoService.pesquisarPorNome("Telefone fixo");
				integrante.adicionarContato(new Contato(tipo, numero.toString()));
				
			} else if (Pattern.matches("[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}", numero)) {
				TipoContato tipo = tipoContatoService.pesquisarPorNome("Telefone celular");
				integrante.adicionarContato(new Contato(tipo, numero.toString()));
			}
		}
		
		Categoria categoria = null;
		
		if (json.has("mensagemFichaCompensacao")) {
			String mensagem = json.get("mensagemFichaCompensacao").asText();
			
			if (mensagem.contains("ROMANCE_ESTUDO") || mensagem.contains("romance_estudo")) {
				categoria = categoriaService.pesquisarporNome("estudo e romance");
				
			} else if (mensagem.contains("ALTERNADO") || mensagem.contains("alternado")) {
				categoria = categoriaService.pesquisarporNome("estudo e romance alternado");
				
			} else if (mensagem.contains("ROMANCE") || mensagem.contains("romance")) {
				categoria = categoriaService.pesquisarporNome("romance");
				
			} else if (mensagem.contains("ESTUDO") || mensagem.contains("estudo")) {
				categoria = categoriaService.pesquisarporNome("estudo");
				
			} else {
				categoria = categoriaService.pesquisarporNome("estudo");
			}
		}
		
		integrante.setCategoria(categoria);
		return integrante;
	}
	
	@PostConstruct
	private void init() {
		objectMapper = new ObjectMapper();
		formaEntrega = formaEntregaService.pesquisarporNome("correios");
		formaPgto = formaPgtoService.pesquisarporNome("boleto bancário");
		frequencia = frequenciaService.pesquisarporNome("mensal");
	}
	
	@Scheduled(initialDelayString = "${integrante.pendente.initial.delay}", fixedDelayString = "${integrante.pendente.fixed.delay}")
	@Transactional
	public void execute() {
		Page<IntegrantePendente> page = pendenteRepositorio.findAll(limite);
		List<IntegrantePendente> pendentes = page.getContent();
		List<IntegranteProcessado> processados = new ArrayList<>(pendentes.size());
		
		for (IntegrantePendente pendente: pendentes) {
			try {
				Integrante integrante = getIntegrante(pendente.getJson());
				Integrante.validar(integrante);
				Integrante integranteSalvo = repositorio.findByCpf(integrante.getCpf());
				
				if (integranteSalvo != null) {
					if (!integranteSalvo.equals(integrante)) {
						integranteSalvo.setNome(integrante.getNome());
						
						if (!integranteSalvo.getContatos().isEmpty()) {
							integrante.getContatos().stream().forEach(c -> {
								List<Contato> contatos = integranteSalvo.getContatos();
								
								for (Contato contato: contatos) {
									if (!contato.equals(c)) {
										integranteSalvo.adicionarContato(c);
									}
								}
							});
							
						} else {
							List<Contato> contatos = integrante.getContatos();
							
							for (Contato contato: contatos) {
								integranteSalvo.adicionarContato(contato);
							}
						}
						
						if (!integranteSalvo.getEnderecos().isEmpty()) {
							integrante.getEnderecos().stream().forEach(e -> {
								List<Endereco> enderecos = integranteSalvo.getEnderecos();
								
								for (Endereco endereco: enderecos) {
									if (!endereco.equals(e)) {
										integranteSalvo.adicionarEndereco(e);
									}
								}
							});
							
						} else {
							List<Endereco> enderecos = integrante.getEnderecos();
							
							for (Endereco endereco: enderecos) {
								integranteSalvo.adicionarEndereco(endereco);
							}
						}
						
						integrante = repositorio.save(integranteSalvo);
						
					} else {
						integrante = integranteSalvo;
					}
					
				} else {
					integrante = repositorio.save(integrante);
				}
				
				Boleto boleto = getBoleto(integrante, pendente.getJson());
				boletoRepositorio.save(boleto);
				IntegranteProcessado processado = new IntegranteProcessado();
				processado.setProximaTentativa(LocalDateTime.now());
				processado.setIntegrante(integrante);
				processados.add(processado);
				
			} catch (Throwable throwable) {
				logger.error("Ocorrer um erro ao tentar salvar os dados de um integrante", throwable);
			}
		}
		
		if (!processados.isEmpty()) {
			pendenteRepositorio.deleteInBatch(pendentes);
			processadoRepositorio.saveAll(processados);
		}
	}
}
