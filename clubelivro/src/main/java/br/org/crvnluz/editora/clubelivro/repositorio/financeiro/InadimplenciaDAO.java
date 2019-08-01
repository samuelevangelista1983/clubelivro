package br.org.crvnluz.editora.clubelivro.repositorio.financeiro;

//@Repository
public class InadimplenciaDAO /*extends BaseDAO*/ {
	/*
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
	*/
}
