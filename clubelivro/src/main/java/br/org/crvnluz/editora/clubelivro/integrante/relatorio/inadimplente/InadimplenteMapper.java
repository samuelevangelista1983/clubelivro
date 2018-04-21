package br.org.crvnluz.editora.clubelivro.integrante.relatorio.inadimplente;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InadimplenteMapper implements RowMapper<Inadimplente> {

	@Override
	public Inadimplente mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Inadimplente(rs.getString("pessoa"), rs.getString("categoria"), rs.getInt("qtd_atraso"));
	}

}
