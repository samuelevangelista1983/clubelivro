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

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

//@RestController
//@RequestMapping("/configuracao")
public class TipoContatoController /*extends CrudController<TipoContato>*/ {
	/*
	@Autowired
	private TipoContatoDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected TipoContato get(Long id) throws Exception {
		return dao.selectById(id);
	}
	
	@Override
	protected List<TipoContato> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected TipoContato salvar(TipoContato tipoContato) {
		return dao.save(tipoContato);
	}
	
	@Override
	protected void validarAtualizacao(TipoContato tipoContato) {}
	
	@Override
	protected void validarInclusao(TipoContato tipoContato) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/tiposcontato")
	public ResponseEntity<Serializable> adicionarTipoContato(@RequestBody TipoContato tipoContato) {
		return criarOuAtualizar(tipoContato);
	}
	
	@PutMapping("/tiposcontato")
	public ResponseEntity<Serializable> atualizarTipoContato(@RequestBody TipoContato tipoContato) {
		return criarOuAtualizar(tipoContato);
	}
	
	@GetMapping("/tiposcontato/{id}")
	public ResponseEntity<Serializable> getTipoContato(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/tiposcontato")
	public ResponseEntity<?> getTiposContato() {
		return listarTodos();
	}
	
	@DeleteMapping("/tiposcontato/{id}")
	public ResponseEntity<Serializable> removerTipoContato(@PathVariable("id") Long id) {
		return remover(id);
	}
	*/
}
