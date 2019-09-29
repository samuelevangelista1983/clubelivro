package br.org.crvnluz.editora.clubelivro.servico.configuracao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.repositorio.configuracao.FormaEntregaRepositorio;

@Component
public class FormaEntregaService {

	@Autowired
	private FormaEntregaRepositorio repositorio;
	
	public FormaEntrega pesquisarporNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("O nome informado está nulo ou vazio");
		}
		
		return repositorio.findByNome(nome);
	}
	
	public List<FormaEntrega> listarTodas() {
		return repositorio.findAll();
	}
}
