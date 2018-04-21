package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BaseDAO {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	// MÉTODOS PROTEGIDOS
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}
	
}
