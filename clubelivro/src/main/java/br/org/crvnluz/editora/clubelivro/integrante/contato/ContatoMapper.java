package br.org.crvnluz.editora.clubelivro.integrante.contato;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ContatoMapper implements RowMapper<Contato> {

	@Override
	public Contato mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contato contato = new Contato();
		contato.setId(rs.getLong("id"));
		contato.setIdPessoa(rs.getLong("id_pessoa"));
		contato.setObservacao(rs.getString("observacao"));
		contato.setValor(rs.getString("valor"));
		contato.setIdTipo(rs.getLong("id_tipo"));
		return contato;
	}

}
