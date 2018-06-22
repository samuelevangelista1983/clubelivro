package br.org.crvnluz.editora.clubelivro.integrante.painel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/painelIntegrante")
public class PainelIntegranteController extends BaseController {
	
	@Autowired
	private PainelIntegranteDAO dao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/pesquisa")
	public ResponseEntity getPainelIntegrante() {
		ResponseEntity response;
		
		try {
			PainelIntegrante painel = dao.getPainelIntegrante();
			response = new ResponseEntity(painel, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
}
