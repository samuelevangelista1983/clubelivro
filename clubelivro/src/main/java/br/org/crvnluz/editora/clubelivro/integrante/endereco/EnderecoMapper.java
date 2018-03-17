package br.org.crvnluz.editora.clubelivro.integrante.endereco;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EnderecoMapper implements RowMapper<Endereco> {

	@Override
	public Endereco mapRow(ResultSet rs, int rowNum) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getLong("id"));
		endereco.setIdPessoa(rs.getLong("id_pessoa"));
		endereco.setIdTipo(rs.getLong("id_tipo"));
		endereco.setCep(rs.getString("cep"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setNumero(rs.getString("numero"));
		endereco.setComplemento(rs.getString("complemento"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setMunicipio(rs.getString("municipio"));
		endereco.setUf(rs.getString("uf"));
		endereco.setObservacao(rs.getString("observacao"));
		return endereco;
	}

}
