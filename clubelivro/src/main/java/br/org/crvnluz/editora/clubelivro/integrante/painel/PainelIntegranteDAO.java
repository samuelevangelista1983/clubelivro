package br.org.crvnluz.editora.clubelivro.integrante.painel;

import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.QueryLoader;

@Repository
public class PainelIntegranteDAO extends BaseDAO {
	
	public PainelIntegrante getPainelIntegrante() throws Exception {
		String sql = QueryLoader.getConsulta("painel_integrantes.sql");
		return jdbcTemplate.queryForObject(sql, new PainelIntegranteMapper());
	}
	
}
