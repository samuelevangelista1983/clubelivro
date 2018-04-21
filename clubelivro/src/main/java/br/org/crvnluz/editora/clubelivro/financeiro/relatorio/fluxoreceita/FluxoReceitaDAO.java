package br.org.crvnluz.editora.clubelivro.financeiro.relatorio.fluxoreceita;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class FluxoReceitaDAO extends BaseDAO {
	
	public List<Object[]> getFluxoReceita(LocalDate inicio, LocalDate fim) throws Exception {
		String sql = QueryLoader.getConsulta("fluxoreceita.sql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("inicio", inicio.toString());
		params.addValue("fim", fim.toString());
		return getNamedParameterJdbcTemplate().query(sql, params, new FluxoReceitaMapper());
	}
	
}
