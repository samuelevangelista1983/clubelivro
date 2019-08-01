package br.org.crvnluz.editora.clubelivro.infra.rest;

public abstract class CrudController<T> extends BaseController {
	/*
	// MÉTODOS PROTEGIDOS
	
	protected abstract void apagar(Long id);
	
	protected ResponseEntity<Serializable> criarOuAtualizar(T t) {
		ResponseEntity<Serializable> response;
		
		try {
			if (t.getId() == null) {
				validarInclusao(t);
				
			} else {
				validarAtualizacao(t);
			}
			
			T added = salvar(t);
			response = new ResponseEntity<>(added, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			throwable.printStackTrace(); // TODO adicionar log
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	protected abstract T get(Long id) throws Exception;
	
	protected ResponseEntity<Serializable> getRecurso(Long id) {
		ResponseEntity<Serializable> response;
		
		try {
			T t = get(id);
			
			if (t == null) {
				response = getNotFoundResponse();
			}
			
			response = new ResponseEntity<>(t, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	protected abstract List<T> listar() throws Exception;
	
	@SuppressWarnings("rawtypes")
	protected ResponseEntity listarTodos() {
		ResponseEntity response;
		
		try {
			List<T> list = listar();
			response = new ResponseEntity<>(list, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	protected ResponseEntity<Serializable> remover(Long id) {
		ResponseEntity<Serializable> response;
		
		try {
			apagar(id);
			response = new ResponseEntity<>(id, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
	protected abstract T salvar(T t);
	
	protected abstract void validarAtualizacao(T t) throws ValidacaoException;
	
	protected abstract void validarInclusao(T t) throws ValidacaoException;
	*/
}
