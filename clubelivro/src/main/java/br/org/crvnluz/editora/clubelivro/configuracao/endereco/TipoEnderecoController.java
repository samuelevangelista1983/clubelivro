package br.org.crvnluz.editora.clubelivro.configuracao.endereco;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/configuracao")
public class TipoEnderecoController extends BaseController<TipoEndereco> {
	
	@Autowired
	private TipoEnderecoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected TipoEndereco get(Long id) throws Exception {
		return dao.getOne(id);
	}
	
	@Override
	protected List<TipoEndereco> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected TipoEndereco salvar(TipoEndereco tipoEndereco) {
		return dao.save(tipoEndereco);
	}
	
	@Override
	protected void validarAtualizacao(TipoEndereco tipoEndereco) {}
	
	@Override
	protected void validarInclusao(TipoEndereco tipoEndereco) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/tiposendereco")
	public ResponseEntity<Serializable> adicionarTipoEndereco(@RequestBody TipoEndereco tipoEndereco) {
		return criarOuAtualizar(tipoEndereco);
	}
	
	@PutMapping("/tiposendereco")
	public ResponseEntity<Serializable> atualizarTipoEndereco(@RequestBody TipoEndereco tipoEndereco) {
		return criarOuAtualizar(tipoEndereco);
	}
	
	@GetMapping("/tiposendereco/{id}")
	public ResponseEntity<Serializable> getTipoEndereco(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/tiposendereco")
	public ResponseEntity<?> getTiposEndereco() {
		return listarTodos();
	}
	
	@DeleteMapping("/tiposendereco/{id}")
	public ResponseEntity<Serializable> removerTipoEndereco(@PathVariable("id") Long id) {
		return remover(id);
	}
	
}
