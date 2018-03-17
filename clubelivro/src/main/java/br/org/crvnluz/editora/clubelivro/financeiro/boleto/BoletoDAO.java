package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;

@Repository
public class BoletoDAO extends BaseDAO<Boleto> {
	
	private FiltroBoletoMapper filtroBoletoMapper;
	
	public BoletoDAO() {
		filtroBoletoMapper = new FiltroBoletoMapper();
	}
	
	private String getSqlPesquisa() {
		StringBuilder sql = new StringBuilder("select b.id, i.id as id_integrante, p.id as id_pessoa, p.nome as pessoa, c.id as id_classificacao, c.nome as classificacao, b.vcto, b.valor_nominal, b.valor_pago, b.situacao, b.numero_banco, b.numero_beneficiario ");
		sql.append("from clube_livro_boleto b ");
		sql.append("inner join clube_livro_integrante i on i.id_pessoa = b.id_sacado ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao where ");
		return sql.toString();
	}
	
	@Override
	protected RowMapper<Boleto> getMapper() {
		return new BoletoMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(Boleto boleto) {
		Map<String, String> map = new HashMap<>();
		map.put("id_pessoa", boleto.getSacado().getPessoa().getId().toString());
		map.put("numero_banco", boleto.getNumeroBanco());
		map.put("numero_beneficiario", boleto.getNumeroBeneficiario());
		map.put("emissao", boleto.getEmissao().toString());
		map.put("vcto", boleto.getVcto().toString());
		map.put("valor_nominal", boleto.getValorNomimal().toString());
		map.put("pgto", boleto.getPgto().toString());
		map.put("efet_credito", boleto.getEfetivacaoCredito().toString());
		map.put("valor_pago", boleto.getValorPago().toString());
		map.put("valor_tarifa", boleto.getValorTarifa().toString());
		map.put("valor_creditado", boleto.getValorCreditado().toString());
		map.put("situacao", boleto.getSituacao().toString());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_boleto";
	}

	// MÉTODOS PÚBLICOS
	
	public List<Boleto> pesquisar(String nome, Long idClassificacao, Long idSituacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("c.id = ? and b.situacao = ? and p.nome like ? order by b.vcto");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {idClassificacao, idSituacao, like}, filtroBoletoMapper);
	}
	
	public List<Boleto> pesquisar(Long idClassificacao, Long idSituacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("c.id = ? and b.situacao = ? order by b.vcto");
		return jdbcTemplate.query(sql.toString(), new Object[] {idClassificacao, idSituacao}, filtroBoletoMapper);
	}
	
	public List<Boleto> pesquisarNomeClassificacao(String nome, Long idClassificacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("c.id = ? and p.nome like ? order by b.vcto");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {idClassificacao, like}, filtroBoletoMapper);
	}
	
	public List<Boleto> pesquisarNomeSituacao(String nome, Long idSituacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("b.situacao = ? and p.nome like ? order by b.vcto");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {idSituacao, like}, filtroBoletoMapper);
	}
	
	public List<Boleto> pesquisarNome(String nome) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("p.nome like ? order by b.vcto");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {like}, filtroBoletoMapper);
	}

	public List<Boleto> pesquisarClassificacao(Long idClassificacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("c.id = ? order by b.vcto");
		return jdbcTemplate.query(sql.toString(), new Object[] {idClassificacao}, filtroBoletoMapper);
	}

	public List<Boleto> pesquisarSituacao(Long idSituacao) {
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		sql.append("b.situacao = ? order by b.vcto");
		return jdbcTemplate.query(sql.toString(), new Object[] {idSituacao}, filtroBoletoMapper);
	}
	
}
