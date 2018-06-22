package br.org.crvnluz.editora.clubelivro.financeiro.painel.receita;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReceitaCategoriaMapper implements RowMapper<Object[]> {

	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Object[] {rs.getString("nome"), rs.getDouble("recebido")};
	}

}
