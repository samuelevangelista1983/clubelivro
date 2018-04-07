package br.org.crvnluz.editora.clubelivro.integrante.documento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class DocumentoDAO extends CrudDAO<Documento> {
	
	@Override
	protected Map<String, String> getMapCamposValores(Documento documento) {
		Map<String, String> map = new HashMap<>();
		map.put("id_pessoa", documento.getIdPessoa().toString());
		map.put("id_tipo", documento.getIdTipo().toString());
		String valor = documento.getValor();
		valor = valor.replace(".", "").replace("-", "");
		map.put("valor", valor);
		return map;
	}

	@Override
	protected RowMapper<Documento> getMapper() {
		return new DocumentoMapper();
	}

	@Override
	protected String getNomeTabela() {
		return "documento";
	}
	
	public Documento findByValor(String valor) {
		valor = valor.replace(".", "").replace("-", "");
		List<Documento> documentos = selectByCampo("valor", valor);
		return !documentos.isEmpty() ? documentos.get(0) : null;
	}
	
	public List<Documento> selectByPessoa(Long idPessoa) {
		return selectByCampo("id_pessoa", idPessoa.toString());
	}
}
