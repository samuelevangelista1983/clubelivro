package br.org.crvnluz.editora.clubelivro.financeiro.mensalidade;

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

import br.org.crvnluz.editora.clubelivro.financeiro.mensalidade.Mensalidade;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/financeiro")
public class MensalidadeController extends BaseController<Mensalidade> {
	
	@Autowired
	private MensalidadeDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected Mensalidade get(Long id) throws Exception {
		return dao.getOne(id);
	}
	
	@Override
	protected List<Mensalidade> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected Mensalidade salvar(Mensalidade mensalidade) {
		return dao.save(mensalidade);
	}
	
	@Override
	protected void validarAtualizacao(Mensalidade mensalidade) {}
	
	@Override
	protected void validarInclusao(Mensalidade mensalidade) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/mensalidades")
	public ResponseEntity<Serializable> adicionarMensalidade(@RequestBody Mensalidade mensalidade) {
		return criarOuAtualizar(mensalidade);
	}
	
	@PutMapping("/mensalidades")
	public ResponseEntity<Serializable> atualizarMensalidade(@RequestBody Mensalidade mensalidade) {
		return criarOuAtualizar(mensalidade);
	}
	
	@GetMapping("/mensalidades/{id}")
	public ResponseEntity<Serializable> getMensalidade(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/mensalidades")
	public ResponseEntity<?> getMensalidades() {
		return listarTodos();
	}
	
	@DeleteMapping("/mensalidades/{id}")
	public ResponseEntity<Serializable> removerMensalidade(@PathVariable("id") Long id) {
		return remover(id);
	}
	
}