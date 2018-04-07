package br.org.crvnluz.editora.clubelivro.integrante.endereco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.infra.persistencia.CrudDAO;

@Repository
public class EnderecoDAO extends CrudDAO<Endereco> {

	@Override
	protected Map<String, String> getMapCamposValores(Endereco endereco) {
		Map<String, String> map = new HashMap<>();
		map.put("id_pessoa", endereco.getIdPessoa().toString());
		map.put("id_tipo", endereco.getIdTipo().toString());
		String cep = endereco.getCep();
		cep = cep.replace(".", "").replace("-", "");
		map.put("cep", cep);
		map.put("logradouro", endereco.getLogradouro());
		map.put("numero", endereco.getNumero());
		map.put("complemento", endereco.getComplemento());
		map.put("bairro", endereco.getBairro());
		map.put("municipio", endereco.getMunicipio());
		map.put("uf", endereco.getUf());
		map.put("observacao", endereco.getObservacao());
		return map;
	}

	@Override
	protected RowMapper<Endereco> getMapper() {
		return new EnderecoMapper();
	}

	@Override
	protected String getNomeTabela() {
		return "endereco";
	}

	// MÉTODOS PÚBLICOS
	
	public List<Endereco> selectByPessoa(Long idPessoa) {
		return selectByCampo("id_pessoa", idPessoa.toString());
	}
}
