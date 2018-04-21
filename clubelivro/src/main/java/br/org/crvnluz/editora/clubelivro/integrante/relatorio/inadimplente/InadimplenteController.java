package br.org.crvnluz.editora.clubelivro.integrante.relatorio.inadimplente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;

@RestController
@RequestMapping("/inadimplencia")
public class InadimplenteController extends BaseController {
	
	@Autowired
	private InadimplenteDAO dao;
	
	// MÉTODOS PÚBLICOS
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/pesquisa")
	public ResponseEntity getInadimplentes(@RequestBody String request) {
		ResponseEntity response;
		
		try {
			Map<String, Object> map = new ObjectMapper().readValue(request, HashMap.class);
			/*
			 * Categoria:
			 * 1 - Estudo
			 * 2 - Romance
			 * 3 - Estudo e romance
			 * 4 - Estudo e romance alternado
			 */
			Long idCategoria = map.get("categoria") != null && StringUtil.stringNaoNulaENaoVazia(map.get("categoria").toString()) ? Long.valueOf(map.get("categoria").toString()) : null;
			List<Inadimplente> inadimplentes = dao.getInadimplentes(idCategoria);
			response = new ResponseEntity(inadimplentes, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	
}
