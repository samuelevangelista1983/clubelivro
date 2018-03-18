package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.eti.sen.utilitarios.tempo.DataUtil;
import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.configuracao.classificacao.Classificacao;
import br.org.crvnluz.editora.clubelivro.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.integrante.pessoa.Pessoa;

public class BoletoMapper implements RowMapper<Boleto> {

	@Override
	public Boleto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Boleto boleto = new Boleto();
		boleto.setId(rs.getLong("id"));
		Integrante integrante = new Integrante();
		integrante.setId(rs.getLong("id_sacado"));
		//integrante.setClassificacao(new Classificacao(rs.getLong("id_classificacao"), rs.getString("classificacao")));
		integrante.setPessoa(new Pessoa(rs.getLong("id_pessoa"), rs.getString("pessoa")));
		boleto.setSacado(integrante);
		String vcto = rs.getString("vcto");
		boleto.setVcto(StringUtil.stringNaoNulaENaoVazia(vcto) ? DataUtil.parserData(vcto, "yyyy-MM-dd") : null);
		boleto.setValorNomimal(rs.getBigDecimal("valor_nominal"));
		boleto.setSituacao(rs.getInt("situacao"));
		String dtEfetivacaoCredito = rs.getString("efet_credito");
		boleto.setEfetivacaoCredito(StringUtil.stringNaoNulaENaoVazia(dtEfetivacaoCredito) ? DataUtil.parserData(dtEfetivacaoCredito, "yyyy-MM-dd") : null);
		String emissao = rs.getString("emissao");
		boleto.setEmissao(StringUtil.stringNaoNulaENaoVazia(emissao) ? DataUtil.parserData(emissao, "yyyy-MM-dd") : null);
		boleto.setNumeroBanco(rs.getString("numero_banco"));
		boleto.setNumeroBeneficiario(rs.getString("numero_beneficiario"));
		String pgto = rs.getString("pgto");
		boleto.setPgto(StringUtil.stringNaoNulaENaoVazia(pgto) ? DataUtil.parserData(pgto, "yyyy-MM-dd") : null);
		boleto.setValorCreditado(rs.getBigDecimal("valor_creditado"));
		boleto.setValorPago(rs.getBigDecimal("valor_pago"));
		boleto.setValorTarifa(rs.getBigDecimal("valor_tarifa"));
		return boleto;
	}

}
