package br.org.crvnluz.editora.clubelivro.controlador.financeiro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.crvnluz.editora.clubelivro.entidade.financeiro.ReceitaCategoria;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.ReceitaDAO;

//@RestController
//@RequestMapping("/receita")
public class ReceitaController extends BaseController {
/*
	@Autowired
	private ReceitaDAO dao;
	
	// MÉTODOS PÚBLICOS
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/pesquisa")
	public ResponseEntity getReceita() {
		ResponseEntity response;
		
		try {
			List<Object[]> dados = dao.getReceitaPorCategoria(LocalDate.now());
			ReceitaCategoria receita = new ReceitaCategoria();
			
			if (!dados.isEmpty()) {
				receita.preencherValores(dados);
			}
			
			response = new ResponseEntity(receita, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	*/
}
