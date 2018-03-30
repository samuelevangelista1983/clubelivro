package br.org.crvnluz.editora.clubelivro.configuracao.frequencia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;

@Repository
public class FrequenciaDAO extends BaseDAO<Frequencia> {

	@Override
	protected RowMapper<Frequencia> getMapper() {
		return new FrequenciaMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(Frequencia frequencia) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", frequencia.getNome());
		map.put("freq_mensal", frequencia.getFreqMensal().toString());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_frequencia";
	}

	// MÉTODOS PÚBLICOS
	
	public List<Frequencia> findAll() {
		return selectAll("nome", true);
	}

}
