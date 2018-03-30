package br.org.crvnluz.editora.clubelivro.configuracao.contato;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TipoContatoMapper implements RowMapper<TipoContato> {

	@Override
	public TipoContato mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipoContato tipo = new TipoContato();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setAtivo(rs.getBoolean("ativo"));
		return tipo;
	}

}
