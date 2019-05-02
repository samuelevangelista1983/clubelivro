package br.org.crvnluz.editora.clubelivro.servico;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.integracao.mensageria.CadastroPessoaIntegrador;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.ContatoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.EnderecoRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.integrante.IntegranteRepositorio;

@Component
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
	
	@Transactional
	public void salvar(Integrante integrante) {
		try {
			Integrante.validar(integrante);
			repositorio.save(integrante);
			contatoRepositorio.save(integrante.getContatos());
			enderecoRepositorio.save(integrante.getEnderecos());
			cadastroPessoa.atualizarCadastroPessoa(integrante);
			
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
	
}
