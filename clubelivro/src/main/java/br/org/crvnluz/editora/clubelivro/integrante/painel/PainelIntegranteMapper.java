package br.org.crvnluz.editora.clubelivro.integrante.painel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PainelIntegranteMapper implements RowMapper<PainelIntegrante> {

	@Override
	public PainelIntegrante mapRow(ResultSet rs, int rowNum) throws SQLException {
		PainelIntegrante painel = new PainelIntegrante();
		painel.setAtivos(rs.getInt("ativos"));
		painel.setInativos(rs.getInt("inativos"));
		painel.setEstudo(rs.getInt("estudo"));
		painel.setRomance(rs.getInt("romance"));
		painel.setEstudoRomance(rs.getInt("estudo_romance"));
		painel.setEstudoRomanceAlternado(rs.getInt("estudo_romance_alternado"));
		painel.setCorreios(rs.getInt("correios"));
		painel.setPresencial(rs.getInt("presencial"));
		painel.setMensal(rs.getInt("mensal"));
		painel.setBimestral(rs.getInt("bimestral"));
		painel.setBoleto(rs.getInt("boleto"));
		painel.setDebito(rs.getInt("cartao_debito"));
		painel.setCredito(rs.getInt("cartao_credito"));
		painel.setDinheiro(rs.getInt("dinheiro"));
		painel.setCheque(rs.getInt("cheque"));
		return painel;
	}

}
