package br.org.crvnluz.editora.clubelivro.configuracao.entrega;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class FormaEntregaDAO extends CrudDAO<FormaEntrega> {

	@Override
	protected RowMapper<FormaEntrega> getMapper() {
		return new FormaEntregaMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(FormaEntrega formaEntrega) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", formaEntrega.getNome());
		map.put("observacao", formaEntrega.getObservacao());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_entrega";
	}

	// MÉTODOS PÚBLICOS
	
	public List<FormaEntrega> findAll() {
		return selectAll("nome", true);
	}

}
