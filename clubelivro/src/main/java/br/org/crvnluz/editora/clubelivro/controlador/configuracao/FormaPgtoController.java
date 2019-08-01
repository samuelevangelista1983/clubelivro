package br.org.crvnluz.editora.clubelivro.controlador.configuracao;

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

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

//@RestController
//@RequestMapping("/configuracao")
public class FormaPgtoController /*extends CrudController<FormaPgto>*/ {
	/*
	@Autowired
	private FormaPgtoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected FormaPgto get(Long id) throws Exception {
		return dao.selectById(id);
	}
	
	@Override
	protected List<FormaPgto> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected FormaPgto salvar(FormaPgto formaPgto) {
		return dao.save(formaPgto);
	}
	
	@Override
	protected void validarAtualizacao(FormaPgto formaPgto) {}
	
	@Override
	protected void validarInclusao(FormaPgto formaPgto) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/formaspgto")
	public ResponseEntity<Serializable> adicionarFormaPgto(@RequestBody FormaPgto formaPgto) {
		return criarOuAtualizar(formaPgto);
	}
	
	@PutMapping("/formaspgto")
	public ResponseEntity<Serializable> atualizarFormaPgto(@RequestBody FormaPgto formaPgto) {
		return criarOuAtualizar(formaPgto);
	}
	
	@GetMapping("/formaspgto/{id}")
	public ResponseEntity<Serializable> getFormaPgto(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/formaspgto")
	public ResponseEntity<?> getFormasPgto() {
		return listarTodos();
	}
	
	@DeleteMapping("/formaspgto/{id}")
	public ResponseEntity<Serializable> removerFormaPgto(@PathVariable("id") Long id) {
		return remover(id);
	}
*/	
}
