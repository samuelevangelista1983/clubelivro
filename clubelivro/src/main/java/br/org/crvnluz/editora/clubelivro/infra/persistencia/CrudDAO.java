package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class CrudDAO<T extends Persistente> extends BaseDAO {
	
	// MÉTODOS PROTEGIDOS
	
	protected abstract RowMapper<T> getMapper();

	protected abstract Map<String, String> getMapCamposValores(T t);
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
	}
	
	protected abstract String getNomeTabela();
	
	protected List<T> selectAll(String campoOrdenacao, boolean ascendente) {
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getNomeTabela()).append(" order by ").append(campoOrdenacao);
		
		if (!ascendente) {
			sql.append(" desc");
		}
		
		return jdbcTemplate.query(sql.toString(), getMapper());
	}

	protected List<T> selectByCampo(String campo, String valor) {
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getNomeTabela()).append(" where ").append(campo).append(" = ?");
		return jdbcTemplate.query(sql.toString(), new Object[] {valor}, getMapper());
	}
	
	protected List<T> selectLikeCampo(String campo, String valor, String campoOrdenacao, boolean ascendente) {
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getNomeTabela()).append(" where ").append(campo).append(" like ? order by ").append(campoOrdenacao);
		
		if (!ascendente) {
			sql.append(" desc");
		}
		
		StringBuilder like = new StringBuilder("%");
		like.append(valor).append("%");
		return jdbcTemplate.query(sql.toString(), new Object[] {like.toString()}, getMapper());
	}

	public void delete(Long id) {
		StringBuilder sql = new StringBuilder("delete from ");
		sql.append(getNomeTabela()).append(" where id = ?");
		jdbcTemplate.update(sql.toString(), new Object[] {id});
	}
	
	public T save(T t) {
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(getNomeTabela()).append(" (");
		Map<String, String> map = getMapCamposValores(t);
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		StringBuilder values = new StringBuilder();
		
		while (iterator.hasNext()) {
			String campo = iterator.next();
			sql.append(campo);
			values.append(":").append(campo);
			
			if (iterator.hasNext()) {
				sql.append(", ");
				values.append(", ");
			}
		}
		
		sql.append(") values (").append(values).append(')');
		MapSqlParameterSource params = new MapSqlParameterSource(map);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql.toString(), params, keyHolder);
		t.setId(keyHolder.getKey().longValue());
		return t;
	}

	public T selectById(Long id) {
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getNomeTabela()).append(" where id = ").append(id);
		return jdbcTemplate.queryForObject(sql.toString(), getMapper());
	}

	public void update(T t) {
		StringBuilder sql = new StringBuilder("update ");
		sql.append(getNomeTabela()).append(" set ");
		Map<String, String> map = getMapCamposValores(t);
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			sql.append(key).append(" = ?");
			
			if (iterator.hasNext()) {
				sql.append(", ");
			}
		}
		
		sql.append(" where id = ").append(t.getId());
		Object[] valores = new Object[keys.size()];
		iterator = keys.iterator();
		int i = 0;
		
		while (iterator.hasNext()) {
			valores[i++] = map.get(iterator.next());
		}
		
		jdbcTemplate.update(sql.toString(), valores);
	}

}
