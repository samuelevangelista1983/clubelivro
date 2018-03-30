package br.org.crvnluz.editora.clubelivro.configuracao.formapgto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FormaPgtoMapper implements RowMapper<FormaPgto> {

	@Override
	public FormaPgto mapRow(ResultSet rs, int rowNum) throws SQLException {
		FormaPgto tipo = new FormaPgto();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setCusto(rs.getDouble("custo"));
		return tipo;
	}

}
