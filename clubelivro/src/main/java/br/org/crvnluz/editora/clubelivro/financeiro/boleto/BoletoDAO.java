package br.org.crvnluz.editora.clubelivro.financeiro.boleto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;

@Repository
public class BoletoDAO extends BaseDAO<Boleto> {
	
	private PesquisaBoletoMapper pesquisaBoletoMapper;
	
	public BoletoDAO() {
		pesquisaBoletoMapper = new PesquisaBoletoMapper();
	}
	
	private String getSqlPesquisa() {
		StringBuilder sql = new StringBuilder("select b.id, i.id as id_integrante, p.id as id_pessoa, p.nome as pessoa, c.id as id_classificacao, c.nome as classificacao, b.vcto, b.valor_nominal, b.valor_pago, b.situacao, b.numero_banco, b.numero_beneficiario ");
		sql.append("from clube_livro_boleto b ");
		sql.append("inner join clube_livro_integrante i on i.id_pessoa = b.id_sacado ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao where 1 = 1 ");
		return sql.toString();
	}
	
	@Override
	protected RowMapper<Boleto> getMapper() {
		return new BoletoMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(Boleto boleto) {
		Map<String, String> map = new HashMap<>();
		map.put("id_sacado", boleto.getSacado().getPessoa().getId().toString());
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
	
	public List<Boleto> pesquisar(String nome, String numBoleto) {
		List<Object> params = new ArrayList<>(2);
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		
		if (StringUtil.stringNaoNulaENaoVazia(nome)) {
			sql.append("and p.nome like ? ");
			params.add(new StringBuilder("%").append(nome).append('%').toString());
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(numBoleto)) {
			sql.append("and b.numero_beneficiario like ? ");
			params.add(new StringBuilder("%").append(numBoleto).append('%').toString());
		}
		
		sql.append("order by b.vcto, p.nome");
		return jdbcTemplate.query(sql.toString(), params.toArray(), pesquisaBoletoMapper);
	}
	
	public List<Boleto> pesquisar(String nome, Long idCategoria, Long idSituacao, LocalDate dtEmissaoInicial, LocalDate dtEmissaoFinal,
									LocalDate dtVctoInicial, LocalDate dtVctoFinal, Long ordenacao, Long campoOrdenacao) {
		
		List<Object> params = new ArrayList<>(9);
		StringBuilder sql = new StringBuilder(getSqlPesquisa());
		
		if (StringUtil.stringNaoNulaENaoVazia(nome)) {
			sql.append("and p.nome like ? ");
			params.add(new StringBuilder("%").append(nome).append('%').toString());
		}
		
		if (idCategoria != null) {
			sql.append("and i.id_classificacao = ? ");
			params.add(idCategoria);
		}
		
		if (idSituacao != null) {
			sql.append("and b.situacao = ? ");
			params.add(idSituacao);
		}
		
		if (dtEmissaoInicial != null && dtEmissaoFinal != null) {
			sql.append("and b.emissao between ? and ? ");
			params.add(dtEmissaoInicial.toString());
			params.add(dtEmissaoFinal.toString());
			
		} else if (dtEmissaoInicial != null && dtEmissaoFinal == null) {
			sql.append("and b.emissao >= ? ");
			params.add(dtEmissaoInicial.toString());
			
		} else if (dtEmissaoInicial == null && dtEmissaoFinal != null) {
			sql.append("and b.emissao <= ? ");
			params.add(dtEmissaoFinal.toString());
		}
		
		if (dtVctoInicial != null && dtVctoFinal != null) {
			sql.append("and b.vcto between ? and ? ");
			params.add(dtVctoInicial.toString());
			params.add(dtVctoFinal.toString());
			
		} else if (dtVctoInicial != null && dtVctoFinal == null) {
			sql.append("and b.vcto >= ? ");
			params.add(dtVctoInicial.toString());
			
		} else if (dtVctoInicial == null && dtVctoFinal != null) {
			sql.append("and b.vcto <= ? ");
			params.add(dtVctoFinal.toString());
		}
		
		if (campoOrdenacao == 0) {
			sql.append("order by p.nome ");
			
		} else if (campoOrdenacao == 1) {
			sql.append("order by b.numero_beneficiario ");
			
		} else if (campoOrdenacao == 2) {
			sql.append("order by b.emissao ");
			
		} else if (campoOrdenacao == 3) {
			sql.append("order by b.vcto ");
		}
		
		if (ordenacao == 1) {
			sql.append("desc");
		}
		
		return jdbcTemplate.query(sql.toString(), params.toArray(), pesquisaBoletoMapper);
	}
	
	@Override
	public Boleto selectById(Long id) {
		StringBuilder sql = new StringBuilder("select p.id as id_pessoa, p.nome as pessoa, b.* from clube_livro_boleto b ");
		sql.append("inner join clube_livro_integrante i on i.id = b.id_sacado inner join pessoa p on p.id = i.id_pessoa where b.id = ").append(id);
		return jdbcTemplate.queryForObject(sql.toString(), getMapper());
	}
	
}
