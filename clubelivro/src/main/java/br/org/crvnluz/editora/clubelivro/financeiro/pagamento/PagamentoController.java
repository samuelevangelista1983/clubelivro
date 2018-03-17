package br.org.crvnluz.editora.clubelivro.financeiro.pagamento;

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

import br.org.crvnluz.editora.clubelivro.financeiro.pagamento.Pagamento;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/financeiro")
public class PagamentoController extends BaseController<Pagamento> {
	
	@Autowired
	private PagamentoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected Pagamento get(Long id) throws Exception {
		return dao.getOne(id);
	}
	
	@Override
	protected List<Pagamento> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected Pagamento salvar(Pagamento pagamento) {
		return dao.save(pagamento);
	}
	
	@Override
	protected void validarAtualizacao(Pagamento pagamento) {}
	
	@Override
	protected void validarInclusao(Pagamento pagamento) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/pagamentos")
	public ResponseEntity<Serializable> adicionarPagamento(@RequestBody Pagamento pagamento) {
		return criarOuAtualizar(pagamento);
	}
	
	@PutMapping("/pagamentos")
	public ResponseEntity<Serializable> atualizarPagamento(@RequestBody Pagamento pagamento) {
		return criarOuAtualizar(pagamento);
	}
	
	@GetMapping("/pagamentos/{id}")
	public ResponseEntity<Serializable> getPagamento(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/pagamentos")
	public ResponseEntity<?> getPagamentos() {
		return listarTodos();
	}
	
	@DeleteMapping("/pagamentos/{id}")
	public ResponseEntity<Serializable> removerPagamento(@PathVariable("id") Long id) {
		return remover(id);
	}
	
}
