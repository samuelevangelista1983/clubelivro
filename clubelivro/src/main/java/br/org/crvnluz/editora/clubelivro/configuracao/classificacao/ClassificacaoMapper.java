package br.org.crvnluz.editora.clubelivro.configuracao.classificacao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ClassificacaoMapper implements RowMapper<Classificacao> {

	@Override
	public Classificacao mapRow(ResultSet rs, int rowNum) throws SQLException {
		Classificacao classificacao = new Classificacao();
		classificacao.setId(rs.getLong("id"));
		classificacao.setNome(rs.getString("nome"));
		return classificacao;
	}

}
