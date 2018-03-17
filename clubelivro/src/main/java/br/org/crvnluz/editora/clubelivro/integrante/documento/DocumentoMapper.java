package br.org.crvnluz.editora.clubelivro.integrante.documento;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DocumentoMapper implements RowMapper<Documento> {

	@Override
	public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
		Documento documento = new Documento();
		documento.setId(rs.getLong("id"));
		documento.setIdPessoa(rs.getLong("id_pessoa"));
		documento.setValor(rs.getString("valor"));
		documento.setIdTipo(rs.getLong("id_tipo"));
		return documento;
	}

}
