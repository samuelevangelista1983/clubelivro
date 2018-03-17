package br.org.crvnluz.editora.clubelivro.configuracao.entrega;

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
public class FormaEntregaController extends BaseController<FormaEntrega> {
	
	@Autowired
	private FormaEntregaDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected FormaEntrega get(Long id) throws Exception {
		return dao.getOne(id);
	}
	
	@Override
	protected List<FormaEntrega> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected FormaEntrega salvar(FormaEntrega formaEntrega) {
		return dao.save(formaEntrega);
	}
	
	@Override
	protected void validarAtualizacao(FormaEntrega formaEntrega) {}
	
	@Override
	protected void validarInclusao(FormaEntrega formaEntrega) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/formasentrega")
	public ResponseEntity<Serializable> adicionarFormaEntrega(@RequestBody FormaEntrega formaEntrega) {
		return criarOuAtualizar(formaEntrega);
	}
	
	@PutMapping("/formasentrega")
	public ResponseEntity<Serializable> atualizarFormaEntrega(@RequestBody FormaEntrega formaEntrega) {
		return criarOuAtualizar(formaEntrega);
	}
	
	@GetMapping("/formasentrega/{id}")
	public ResponseEntity<Serializable> getFormaEntrega(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/formasentrega")
	public ResponseEntity<?> getFormasEntrega() {
		return listarTodos();
	}
	
	@DeleteMapping("/formasentrega/{id}")
	public ResponseEntity<Serializable> removerFormaEntrega(@PathVariable("id") Long id) {
		return remover(id);
	}
	
}
