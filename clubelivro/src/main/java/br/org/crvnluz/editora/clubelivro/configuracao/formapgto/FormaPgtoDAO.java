package br.org.crvnluz.editora.clubelivro.configuracao.formapgto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class FormaPgtoDAO extends CrudDAO<FormaPgto> {

	@Override
	protected RowMapper<FormaPgto> getMapper() {
		return new FormaPgtoMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(FormaPgto formaPgto) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", formaPgto.getNome());
		map.put("custo", formaPgto.getCusto().toString());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_forma_pgto";
	}

	// MÉTODOS PÚBLICOS
	
	public List<FormaPgto> findAll() {
		return selectAll("nome", true);
	}

}
