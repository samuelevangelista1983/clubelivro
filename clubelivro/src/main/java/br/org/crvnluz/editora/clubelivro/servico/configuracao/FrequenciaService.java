package br.org.crvnluz.editora.clubelivro.servico.configuracao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.repositorio.configuracao.FrequenciaRepositorio;

@Component
public class FrequenciaService {

	@Autowired
	private FrequenciaRepositorio repositorio;
	
	public Frequencia pesquisarporNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("O nome informado está nulo ou vazio");
		}
		
		return repositorio.findByNome(nome);
	}
	
	public List<Frequencia> listarTodas() {
		return repositorio.findAll();
	}
}
