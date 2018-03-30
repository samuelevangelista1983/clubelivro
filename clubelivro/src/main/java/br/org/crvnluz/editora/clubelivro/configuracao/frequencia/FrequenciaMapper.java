package br.org.crvnluz.editora.clubelivro.configuracao.frequencia;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FrequenciaMapper implements RowMapper<Frequencia> {

	@Override
	public Frequencia mapRow(ResultSet rs, int rowNum) throws SQLException {
		Frequencia tipo = new Frequencia();
		tipo.setId(rs.getLong("id"));
		tipo.setNome(rs.getString("nome"));
		tipo.setFreqMensal(rs.getInt("freq_mensal"));
		return tipo;
	}

}
