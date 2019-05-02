package br.org.crvnluz.editora.clubelivro.integracao.mensageria;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import crvnluz.pessoas.entidade.Atualizacao;
import crvnluz.pessoas.entidade.Documento;
import crvnluz.pessoas.entidade.Informacao;
import crvnluz.pessoas.entidade.Operacao;
import crvnluz.pessoas.entidade.Pessoa;
import crvnluz.pessoas.mensageria.Mensagem;

@Component
public class CadastroPessoaIntegrador {

	private final Logger logger = LoggerFactory.getLogger(CadastroPessoaIntegrador.class);
	
	@Value("${cadastro.pessoas.rest.service}")
	private String pessoasRestServiceURL;
	@Value("${activemq.broker.queue.cadastro.pessoas}")
	private String queueCadastroPessoa;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	private Pessoa pesquisarPessoa(String cpf) {
		Pessoa pessoa = null;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			String url = pessoasRestServiceURL.concat("/").concat(Documento.CPF.getNome()).concat("/").concat(cpf);
			ResponseEntity<Pessoa> responseEntity = restTemplate.getForEntity(url, Pessoa.class);
			
			if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				pessoa = (Pessoa) responseEntity.getBody();
				
			} else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				logger.info(String.format("Não foi encontrada uma pessoa no cadastro de pessoas com o CPF %s", cpf));
				
			} else if (responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				logger.info("Erro interno do serviço Rest do cadastro de pessoas");
				
			} else {
				logger.info("Status code retornado pelo serviço Rest do cadastro de pessoas desconhecido");
			}
			
		} catch (HttpClientErrorException httpClientErroException) {
			HttpStatus statusCode = httpClientErroException.getStatusCode();
			
			if (statusCode.equals(HttpStatus.NOT_FOUND)) {
				logger.info(String.format("Não foi encontrada uma pessoa no cadastro de pessoas com o CPF %s", cpf));
				
			} else if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				logger.info("Erro interno do serviço Rest do cadastro de pessoas", httpClientErroException);
				
			} else {
				logger.info("Status code retornado pelo serviço Rest do cadastro de pessoas desconhecido");
			}
			
		} catch (Throwable t) {
			logger.error("Houve um erro ao pesquisar a pessoa", t);
		}
		
		return pessoa;
	}
	
	private Mensagem getMensagem(Integrante integrante) {
		Pessoa pessoaEncontrada = pesquisarPessoa(integrante.getCpf());
		Pessoa pessoa = new Pessoa(null, integrante.getNome(), integrante.getNascimento());
		pessoa.getAlteracoes().add(new Atualizacao(LocalDateTime.now(), "CLUBE DO LIVRO"));
		
		if (pessoaEncontrada != null) {
			pessoa.adicionarDocumento(new Informacao(Documento.CPF.getNome(), integrante.getCpf(), Operacao.ADICIONAR.getValor()));
			
		} else {
			pessoa.adicionarDocumento(new Informacao(Documento.CPF.getNome(), integrante.getCpf(), Operacao.ATUALIZAR.getValor()));
		}
		
		for (Contato contato: integrante.getContatos()) {
			Informacao informacao = new Informacao(contato.getTipo().getNome(), contato.getValor(), Operacao.ADICIONAR.getValor(), contato.getObservacao());
			pessoa.adicionarContato(informacao);
			
			if (pessoaEncontrada != null) {
				if (pessoaEncontrada.getContatos().contains(informacao)) {
					informacao.setOperacao(Operacao.ATUALIZAR.getValor());
				}
			}
		}
		
		for (Endereco end: integrante.getEnderecos()) {
			crvnluz.pessoas.entidade.Endereco endereco = new crvnluz.pessoas.entidade.Endereco(Operacao.ADICIONAR.getValor());
			endereco.setId(end.getId().toString());
			endereco.setBairro(end.getBairro());
			endereco.setCep(end.getCep());
			endereco.setCidade(end.getCidade());
			endereco.setComplemento(end.getComplemento());
			endereco.setLogradouro(end.getLogradouro());
			endereco.setNumero(end.getNumero());
			endereco.setObservacao(end.getObservacao());
			endereco.setUf(end.getUf());
			pessoa.adicionarEndereco(endereco);
			
			if (pessoaEncontrada != null) {
				if (pessoaEncontrada.getEnderecos().contains(endereco)) {
					endereco.setOperacao(Operacao.ATUALIZAR.getValor());
				}
			}
		}
		
		Mensagem mensagem = null;
		
		if (pessoaEncontrada != null) {
			pessoa.setId(pessoaEncontrada.getId());
			mensagem = new Mensagem(Mensagem.ATUALIZAR, pessoa);
			
		} else {
			mensagem = new Mensagem(Mensagem.ADICIONAR, pessoa);
		}
		
		return mensagem;
	}
	
	public void atualizarCadastroPessoa(Integrante integrante) {
		try {
			Mensagem mensagem = getMensagem(integrante);
			jmsTemplate.convertAndSend(queueCadastroPessoa, mensagem);
			
		} catch (Throwable t) {
			logger.error("Ocorreu um erro ao tentar atualizar o cadastro pessoas", t);
		}
	}
}
