package br.org.crvnluz.editora.clubelivro.servico.integrante;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.sen.endereco.EnderecoNaoEncontradoException;
import br.eti.sen.endereco.Localizador;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.infra.rest.CrudController;

//@RestController
//@RequestMapping("/endereco")
public class EnderecoService /*extends CrudController<Endereco>*/ {
	/*
	@Override
	protected void apagar(Long id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Endereco get(Long id) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected List<Endereco> listar() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected Endereco salvar(Endereco t) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void validarAtualizacao(Endereco endereco) {}
	
	@Override
	protected void validarInclusao(Endereco endereco) {}
	
	// MÉTODOS PÚBLICOS
	
	@GetMapping("/consulta/{cep}")
	public ResponseEntity<Serializable> consultar(@PathVariable("cep") String cep) {
		ResponseEntity<Serializable> response;
		
		try {
			Endereco endereco = new Endereco();
			new Localizador().pesquisarEndereco(endereco, cep);
			response = new ResponseEntity<Serializable>(endereco, HttpStatus.OK);
			
		} catch (EnderecoNaoEncontradoException ex) {
			response = getNotFoundResponse();
			
		} catch (IllegalArgumentException ex) {
			response = getUnprocessableEntityResponse(ex.getMessage());
		}
		
		return response;
	}
*/
}
