package br.org.crvnluz.editora.clubelivro.integrante;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;
import br.org.crvnluz.editora.clubelivro.integrante.endereco.Endereco;

@Repository
public class IntegranteDAO extends CrudDAO<Integrante> {
	
	@Override
	protected Map<String, String> getMapCamposValores(Integrante integrante) {
		Map<String, String> map = new HashMap<>();
		map.put("id_pessoa", integrante.getPessoa().getId().toString());
		map.put("id_documento", integrante.getDocumento().getId().toString());
		Endereco cobranca = integrante.getEnderecoCobranca();
		map.put("id_endereco_cobranca", cobranca != null && cobranca.getId() != null ? integrante.getEnderecoCobranca().getId().toString() : null);
		Endereco entrega = integrante.getEnderecoEntrega();
		map.put("id_endereco_entrega", entrega != null && entrega.getId() != null ? integrante.getEnderecoEntrega().getId().toString() : null);
		map.put("id_entrega", integrante.getFormaEntrega().getId().toString());
		map.put("id_classificacao", integrante.getClassificacao().getId().toString());
		map.put("id_frequencia", integrante.getFrequencia().getId().toString());
		map.put("id_forma_pgto_pref", integrante.getFormaPgtoPref().getId().toString());
		map.put("dia_pgto_pref", integrante.getDiaPgtoPref().toString());
		map.put("ativo", integrante.getAtivo() ? "1" : "0");
		map.put("dt_inativo", integrante.getDtDesativacao() != null ? integrante.getDtDesativacao().toString() : null);
		map.put("dt_cadastro", integrante.getDtCadastro().toString());
		return map;
	}

	@Override
	protected RowMapper<Integrante> getMapper() {
		return new IntegranteMapper();
	}

	@Override
	protected String getNomeTabela() {
		return "clube_livro_integrante";
	}

	// MÉTODOS PÚBLICOS
	
	public List<Integrante> pesquisar(String nome) {
		StringBuilder sql = new StringBuilder("select * from clube_livro_integrante i ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join endereco ec on ec.id = i.id_endereco_cobranca ");
		sql.append("inner join endereco ee on ee.id = i.id_endereco_entrega ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao ");
		sql.append("where p.nome like ? order by p.nome");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {like}, getMapper());
	}
	
	public List<Integrante> pesquisar(Long idCategoria) {
		StringBuilder sql = new StringBuilder("select * from clube_livro_integrante i ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join endereco ec on ec.id = i.id_endereco_cobranca ");
		sql.append("inner join endereco ee on ee.id = i.id_endereco_entrega ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao ");
		sql.append("where i.id_classificacao = ? order by p.nome");
		return jdbcTemplate.query(sql.toString(), new Object[] {idCategoria}, getMapper());
	}
	
	public List<Integrante> pesquisar(String nome, Long idCategoria) {
		StringBuilder sql = new StringBuilder("select * from clube_livro_integrante i ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join endereco ec on ec.id = i.id_endereco_cobranca ");
		sql.append("inner join endereco ee on ee.id = i.id_endereco_entrega ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao ");
		sql.append("where i.id_classificacao = ? and p.nome like ? order by p.nome");
		StringBuilder like = new StringBuilder("%");
		like.append(nome).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {idCategoria, like}, getMapper());
	}
	
	public List<Integrante> selectAll() {
		StringBuilder sql = new StringBuilder("select * from clube_livro_integrante i ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join endereco ec on ec.id = i.id_endereco_cobranca ");
		sql.append("inner join endereco ee on ee.id = i.id_endereco_entrega ");
		sql.append("order by p.nome");
		return jdbcTemplate.query(sql.toString(), getMapper());
	}

	@Override
	public Integrante selectById(Long id) {
		StringBuilder sql = new StringBuilder("select * from clube_livro_integrante i ");
		sql.append("inner join pessoa p on p.id = i.id_pessoa ");
		sql.append("inner join endereco ec on ec.id = i.id_endereco_cobranca ");
		sql.append("inner join endereco ee on ee.id = i.id_endereco_entrega ");
		sql.append("inner join clube_livro_classificacao c on c.id = i.id_classificacao ");
		sql.append("where i.id = ?");
		return jdbcTemplate.queryForObject(sql.toString(), new Object[] {id}, getMapper());
	}

}
