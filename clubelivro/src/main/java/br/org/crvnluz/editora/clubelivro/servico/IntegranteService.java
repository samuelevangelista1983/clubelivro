package br.org.crvnluz.editora.clubelivro.servico;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.integracao.job.CadastroPessoaIntegrador;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.ContatoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.EnderecoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.IntegranteRepositorio;


public class IntegranteService {
	
	private final Logger logger = LoggerFactory.getLogger(IntegranteService.class);
	
	@Autowired
	private ContatoRepositorio contatoRepositorio;
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	@Autowired
	private IntegranteRepositorio repositorio;
	@Autowired
	private CadastroPessoaIntegrador cadastroPessoa;
	
	public void atualizar(Integrante integrante) {
		try {
			cadastroPessoa.atualizarCadastroPessoa(integrante);
			
		} catch (Throwable t) {
			logger.error("Ocorreu um erro ao atualizar o integrante no cadastro de pessoas", t);
		}
	}
	/*
	@Transactional
	public void salvar(Integrante integrante) {
		try {
			Integrante.validar(integrante);
			Integrante integranteSalvo = repositorio.save(integrante);
			List<Contato> contatos = integrante.getContatos();
			contatos.stream().forEach(c -> c.setIntegrante(integranteSalvo));
			contatoRepositorio.save(contatos);
			List<Endereco> enderecos = integrante.getEnderecos();
			enderecos.stream().forEach(e -> e.setIntegrante(integranteSalvo));
			enderecoRepositorio.save(enderecos);
			
		} catch (ValidacaoException e) {
			logger.error("Ocorreu um erro ao salvar integrante", e);
		}
	}
	
	@Transactional
	public Integrante pesquisarPorCPF(String cpf) {
		if (!StringUtils.hasText(cpf)) {
			throw new IllegalArgumentException("O CPF informado está nulo ou vazio");
		}
		
		Integrante integrante = repositorio.findByCpf(cpf);
		
		if (integrante != null) {
			try {
				integrante = (Integrante) integrante.clone();
				
			} catch (Throwable t) {
				logger.error("Ocorreu um erro ao pesquisar o integrante", t);
			}
		}
		
		return integrante;
	}
	*/
}
