package br.org.crvnluz.editora.clubelivro.financeiro.painel.inadimplencia;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class InadimplenciaDAO extends BaseDAO {
	
	public List<Object[]> getInadimplenciaPorCategoria(LocalDate dataLimite) throws Exception {
		String sql = QueryLoader.getConsulta("inadimplencia_categoria.sql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("datalimite", dataLimite.toString());
		return getNamedParameterJdbcTemplate().query(sql, params, new InadimplenciaCategoriaMapper());
	}
	
	public List<Object[]> getInadimplenciaMensal(LocalDate dataLimite) throws Exception {
		String sql = QueryLoader.getConsulta("inadimplencia_mensal.sql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("datalimite", dataLimite.toString());
		return getNamedParameterJdbcTemplate().query(sql, params, new InadimplenciaMensalMapper());
	}
	
}
