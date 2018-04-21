package br.org.crvnluz.editora.clubelivro.integrante.relatorio.inadimplente;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class InadimplenteDAO extends BaseDAO {
	
	public List<Inadimplente> getInadimplentes(Long idCategoria) throws Exception {
		String sql = QueryLoader.getConsulta("inadimplentes.sql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idClassificacao", idCategoria);
		return getNamedParameterJdbcTemplate().query(sql, params, new InadimplenteMapper());
	}
	
}
