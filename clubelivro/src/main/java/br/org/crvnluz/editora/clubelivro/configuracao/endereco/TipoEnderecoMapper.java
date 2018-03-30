package br.org.crvnluz.editora.clubelivro.configuracao.endereco;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TipoEnderecoMapper implements RowMapper<TipoEndereco> {

	@Override
	public TipoEndereco mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipoEndereco tipo = new TipoEndereco();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setAtivo(rs.getBoolean("ativo"));
		return tipo;
	}

}
