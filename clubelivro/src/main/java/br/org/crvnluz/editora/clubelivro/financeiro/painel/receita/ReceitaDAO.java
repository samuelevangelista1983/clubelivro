package br.org.crvnluz.editora.clubelivro.financeiro.painel.receita;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class ReceitaDAO extends BaseDAO {
	
	public List<Object[]> getReceitaPorCategoria(LocalDate dataLimite) throws Exception {
		String sql = QueryLoader.getConsulta("receita_categoria.sql");
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("datalimite", dataLimite.toString());
		return getNamedParameterJdbcTemplate().query(sql, params, new ReceitaCategoriaMapper());
	}
	
}
