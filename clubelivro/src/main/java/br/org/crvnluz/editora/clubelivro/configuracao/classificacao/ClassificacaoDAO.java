package br.org.crvnluz.editora.clubelivro.configuracao.classificacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class ClassificacaoDAO extends CrudDAO<Classificacao> {

	@Override
	protected RowMapper<Classificacao> getMapper() {
		return new ClassificacaoMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(Classificacao classificacao) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", classificacao.getNome());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_classificacao";
	}
	
	// MÉTODOS PÚBLICOS
	
	public List<Classificacao> findAll() {
		return selectAll("nome", true);
	}
	
}
