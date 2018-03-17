package br.org.crvnluz.editora.clubelivro.integrante.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.eti.sen.utilitarios.tempo.DataUtil;

public class PessoaMapper implements RowMapper<Pessoa> {

	@Override
	public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getLong("id"));
		String nascimento = rs.getString("nascimento");
		pessoa.setNascimento(nascimento != null ? DataUtil.parserData(nascimento, "yyyy-MM-dd") : null);
		pessoa.setNome(rs.getString("nome"));
		return pessoa;
	}

}
