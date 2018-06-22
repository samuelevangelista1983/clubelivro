package br.org.crvnluz.editora.clubelivro.financeiro.painel.inadimplencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InadimplenciaCategoriaMapper implements RowMapper<Object[]> {
	
	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Object[] {rs.getString("nome"), rs.getDouble("valor")};
	}
	
}
