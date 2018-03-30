package br.org.crvnluz.editora.clubelivro.configuracao.documento;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TipoDocumentoMapper implements RowMapper<TipoDocumento> {

	@Override
	public TipoDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipoDocumento tipo = new TipoDocumento();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setAtivo(rs.getBoolean("ativo"));
		return tipo;
	}

}
