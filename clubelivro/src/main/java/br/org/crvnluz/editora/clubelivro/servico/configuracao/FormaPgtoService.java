package br.org.crvnluz.editora.clubelivro.servico.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.repositorio.configuracao.FormaPgtoRepositorio;

@Component
public class FormaPgtoService {

	@Autowired
	private FormaPgtoRepositorio repositorio;
	
	public FormaPgto pesquisarporNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("O nome informado está nulo ou vazio");
		}
		
		return repositorio.findByNome(nome);
	}
}
