package br.org.crvnluz.editora.clubelivro.servico.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.repositorio.configuracao.TipoContatoRepositorio;

@Component
public class TipoContatoService {

	@Autowired
	private TipoContatoRepositorio repositorio;
	
	public TipoContato pesquisarPorNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("O nome informado está nulo ou vazio");
		}
		
		return repositorio.findByNome(nome);
	}
	
}
