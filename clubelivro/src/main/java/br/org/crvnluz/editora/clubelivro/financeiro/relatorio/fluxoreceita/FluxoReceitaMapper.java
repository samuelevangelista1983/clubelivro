package br.org.crvnluz.editora.clubelivro.financeiro.relatorio.fluxoreceita;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FluxoReceitaMapper implements RowMapper<Object[]> {
	
	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Object[] {rs.getInt("ano"), rs.getInt("mes"), rs.getDouble("previsto"), rs.getDouble("realizado")};
	}
	
}
