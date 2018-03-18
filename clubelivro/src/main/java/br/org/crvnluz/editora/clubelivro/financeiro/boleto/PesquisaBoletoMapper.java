package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.configuracao.classificacao.Classificacao;
import br.org.crvnluz.editora.clubelivro.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.Pessoa;

public class PesquisaBoletoMapper implements RowMapper<Boleto> {

	@Override
	public Boleto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Boleto boleto = new Boleto();
		boleto.setId(rs.getLong("id"));
		Integrante integrante = new Integrante();
		integrante.setId(rs.getLong("id_integrante"));
		integrante.setClassificacao(new Classificacao(rs.getLong("id_classificacao"), rs.getString("classificacao")));
		integrante.setPessoa(new Pessoa(rs.getLong("id_pessoa"), rs.getString("pessoa")));
		boleto.setSacado(integrante);
		String vcto = rs.getString("vcto");
		boleto.setVcto(StringUtil.stringNaoNulaENaoVazia(vcto) ? DataUtil.parserData(vcto, "yyyy-MM-dd") : null);
		boleto.setValorNomimal(rs.getBigDecimal("valor_nominal"));
		boleto.setSituacao(rs.getInt("situacao"));
		boleto.setNumeroBanco(rs.getString("numero_banco"));
		boleto.setNumeroBeneficiario(rs.getString("numero_beneficiario"));
		boleto.setValorPago(rs.getBigDecimal("valor_pago"));
		return boleto;
	}

}
