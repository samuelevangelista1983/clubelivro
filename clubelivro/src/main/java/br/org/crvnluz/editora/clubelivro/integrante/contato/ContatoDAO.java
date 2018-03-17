package br.org.crvnluz.editora.clubelivro.integrante.contato;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.BaseDAO;

@Repository
public class ContatoDAO extends BaseDAO<Contato> {

	@Override
	protected Map<String, String> getMapCamposValores(Contato contato) {
		Map<String, String> map = new HashMap<>();
		map.put("id_pessoa", contato.getIdPessoa().toString());
		map.put("id_tipo", contato.getIdTipo().toString());
		String valor = contato.getValor();
		valor = valor.replace(" ", "").replace("-", "");
		map.put("valor", valor);
		map.put("observacao", contato.getObservacao());
		return map;
	}

	@Override
	protected RowMapper<Contato> getMapper() {
		return new ContatoMapper();
	}

	@Override
	protected String getNomeTabela() {
		return "contato";
	}

	// MÉTODOS PÚBLICOS
	
	public List<Contato> selectByPessoa(Long idPessoa) {
		return selectByCampo("id_pessoa", idPessoa.toString());
	}
}
