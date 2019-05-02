package br.org.crvnluz.editora.clubelivro.configuracao.classificacao;

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

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

//@RestController
//@RequestMapping("/configuracao")
public class ClassificacaoController /*extends CrudController<Classificacao>*/ {
	/*
	@Autowired
	private ClassificacaoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected Classificacao get(Long id) throws Exception {
		return dao.selectById(id);
	}
	
	@Override
	protected List<Classificacao> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected Classificacao salvar(Classificacao classificacao) {
		return dao.save(classificacao);
	}
	
	@Override
	protected void validarAtualizacao(Classificacao classificacao) {}
	
	@Override
	protected void validarInclusao(Classificacao classificacao) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/classificacoes")
	public ResponseEntity<Serializable> adicionarClassificacao(@RequestBody Classificacao classificacao) {
		return criarOuAtualizar(classificacao);
	}
	
	@PutMapping("/classificacoes")
	public ResponseEntity<Serializable> atualizarClassificacao(@RequestBody Classificacao classificacao) {
		return criarOuAtualizar(classificacao);
	}
	
	@GetMapping("/classificacoes/{id}")
	public ResponseEntity<Serializable> getClassificacao(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/classificacoes")
	public ResponseEntity<?> getClassificacoes() {
		return listarTodos();
	}
	
	@DeleteMapping("/classificacoes/{id}")
	public ResponseEntity<Serializable> removerClassificacao(@PathVariable("id") Long id) {
		return remover(id);
	}
	*/
}
