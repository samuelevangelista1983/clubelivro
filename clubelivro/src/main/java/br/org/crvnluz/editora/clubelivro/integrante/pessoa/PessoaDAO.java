package br.org.crvnluz.editora.clubelivro.integrante.pessoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class PessoaDAO extends CrudDAO<Pessoa> {

	@Override
	protected Map<String, String> getMapCamposValores(Pessoa pessoa) {
		Map<String, String> map = new HashMap<>();
		map.put("nome", pessoa.getNome());
		map.put("nascimento", pessoa.getNascimento() != null ? pessoa.getNascimento().toString() : null);
		return map;
	}

	@Override
	protected RowMapper<Pessoa> getMapper() {
		return new PessoaMapper();
	}

	@Override
	protected String getNomeTabela() {
		return "pessoa";
	}
	
	// MÉTODOS PÚBLICOS
	
	public List<Pessoa> findByNome(String nome) {
		return selectLikeCampo("nome", nome, "nome", true);
	}
	
	public Pessoa findByNomeAndIdNot(String nome, Long id) {
		List<Pessoa> pessoas = selectByCampo("nome", nome);
		Pessoa pessoa = null;
		
		if (!pessoas.isEmpty()) {
			for (Pessoa p: pessoas) {
				if (p.getId() != id) {
					pessoa = p;
				}
			}
		}
		
		return pessoa;
	}
	
	public Integer contarPessoasMesmoCpf(String valor, Long id) {
		String sql = "select count(*) from pessoa inner join documento on id_pessoa = pessoa.id where valor = ? and pessoa.id <> ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, new Object[] {valor, id});
	}

}
