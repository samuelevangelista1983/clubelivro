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

import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

//@RestController
//@RequestMapping("/configuracao")
public class FrequenciaController /*0extends CrudController<Frequencia>*/ {
	/*
	@Autowired
	private FrequenciaDAO dao;
	
	// MÉTODOS PROTEGIDOS
	
	@Override
	protected void apagar(Long id) {
		dao.delete(id);
	}
	
	@Override
	protected Frequencia get(Long id) throws Exception {
		return dao.selectById(id);
	}
	
	@Override
	protected List<Frequencia> listar() throws Exception {
		return dao.findAll();
	}
	
	@Override
	protected Frequencia salvar(Frequencia frequencia) {
		return dao.save(frequencia);
	}

	@Override
	protected void validarAtualizacao(Frequencia t) {}
	
	@Override
	protected void validarInclusao(Frequencia frequencia) {}
	
	// MÉTODOS PÚBLICOS
	
	@PostMapping("/frequencias")
	public ResponseEntity<Serializable> adicionarFrequencia(@RequestBody Frequencia frequencia) {
		return criarOuAtualizar(frequencia);
	}
	
	@PutMapping("/frequencias")
	public ResponseEntity<Serializable> atualizarFrequencia(@RequestBody Frequencia frequencia) {
		return criarOuAtualizar(frequencia);
	}
	
	@GetMapping("/frequencias/{id}")
	public ResponseEntity<Serializable> getFrequencia(@PathVariable("id") Long id) {
		return getRecurso(id);
	}
	
	@GetMapping("/frequencias")
	public ResponseEntity<?> getFrequencias() {
		return listarTodos();
	}
	
	@DeleteMapping("/frequencias/{id}")
	public ResponseEntity<Serializable> removerFrequencia(@PathVariable("id") Long id) {
		return remover(id);
	}
	*/
}
