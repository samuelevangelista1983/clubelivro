package br.org.crvnluz.editora.clubelivro.configuracao.endereco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;

@Repository
public class TipoEnderecoDAO extends BaseDAO<TipoEndereco> {

	@Override
	protected RowMapper<TipoEndereco> getMapper() {
		return new TipoEnderecoMapper();
	}

	@Override
	protected Map<String, String> getMapCamposValores(TipoEndereco tipo) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", tipo.getNome());
		map.put("ativo", tipo.getAtivo().toString());
		return map;
	}

	@Override
	protected String getNomeTabela() {
		return "tipo_endereco";
	}

	@Override
	protected List<TipoEndereco> selectAll(String campoOrdenacao, boolean ascendente) {
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getNomeTabela()).append(" where ativo = ? order by ").append(campoOrdenacao);
		
		if (!ascendente) {
			sql.append(" desc");
		}
		
		return jdbcTemplate.query(sql.toString(), new Object[] {true}, getMapper());
	}

	// MÉTODOS PÚBLICOS
	
	public List<TipoEndereco> findAll() {
		return this.selectAll("nome", true);
	}

}
