package br.org.crvnluz.editora.clubelivro.configuracao.entrega;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FormaEntregaMapper implements RowMapper<FormaEntrega> {

	@Override
	public FormaEntrega mapRow(ResultSet rs, int rowNum) throws SQLException {
		FormaEntrega tipo = new FormaEntrega();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setObservacao(rs.getString("observacao"));
		return tipo;
	}

}
