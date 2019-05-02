package crvnluz.pessoas.rest;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crvnluz.pessoas.entidade.Pessoa;
import crvnluz.pessoas.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaRest {
	
	private final Logger logger = LoggerFactory.getLogger(PessoaRest.class);
	
	@Autowired
	private PessoaService service;
	
	@GetMapping("/{nome}/{valor}")
	public ResponseEntity<Serializable> getPessoa(@PathVariable String nome, @PathVariable String valor) {
		ResponseEntity<Serializable> response;
		
		try {
			Pessoa pessoa = service.pesquisarPorDocumento(nome, valor);
			
			if (pessoa != null) {
				response = new ResponseEntity<>(pessoa, HttpStatus.OK);
				
			} else {
				response = new ResponseEntity<Serializable>("Recurso não encontrado", HttpStatus.NOT_FOUND);
			}
			
		} catch (Throwable throwable) {
			logger.error("Ocorreu um erro ao pesquisar a pessoa por documento", throwable);
			response = new ResponseEntity<Serializable>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
