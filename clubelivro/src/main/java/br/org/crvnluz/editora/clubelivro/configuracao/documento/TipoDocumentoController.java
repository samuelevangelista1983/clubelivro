package br.org.crvnluz.editora.clubelivro.configuracao.documento;

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
public class TipoDocumentoController extends BaseController<TipoDocumento> {
	
	@Autowired
	private TipoDocumentoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected TipoDocumento get(Long id) throws Exception {
		return dao.getOne(id);
	}
	
	@Override
	protected List<TipoDocumento> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected TipoDocumento salvar(TipoDocumento tipoDocumento) {
		return dao.save(tipoDocumento);
	}
	
	@Override
	protected void validarAtualizacao(TipoDocumento tipoDocumento) {}
	
	@Override
	protected void validarInclusao(TipoDocumento tipoDocumento) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/tiposdocumento")
	public ResponseEntity<Serializable> adicionarTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
		return criarOuAtualizar(tipoDocumento);
	}
	
	@PutMapping("/tiposdocumento")
	public ResponseEntity<Serializable> atualizarTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
		return criarOuAtualizar(tipoDocumento);
	}
	
	@GetMapping("/tiposdocumento/{id}")
	public ResponseEntity<Serializable> getTipoDocumento(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/tiposdocumento")
	public ResponseEntity<?> getTiposDocumento() {
		return listarTodos();
	}
	
	@DeleteMapping("/tiposdocumento/{id}")
	public ResponseEntity<Serializable> removerTipoDocumento(@PathVariable("id") Long id) {
		return remover(id);
	}
	
}
