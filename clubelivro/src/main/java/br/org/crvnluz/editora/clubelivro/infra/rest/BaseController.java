package br.org.crvnluz.editora.clubelivro.infra.rest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
	
	protected ResponseEntity<Serializable> getNotFoundResponse() {
		return new ResponseEntity<Serializable>("Recurso não encontrado", HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<Serializable> getInternalServerErrorResponse(Throwable t) {
		return new ResponseEntity<Serializable>(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected ResponseEntity<Serializable> getUnprocessableEntityResponse(String mensagem) {
		return new ResponseEntity<Serializable>(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
