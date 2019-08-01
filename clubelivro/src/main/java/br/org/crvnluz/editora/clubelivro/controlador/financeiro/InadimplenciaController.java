package br.org.crvnluz.editora.clubelivro.controlador.financeiro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.crvnluz.editora.clubelivro.entidade.financeiro.Inadimplencia;
import br.org.crvnluz.editora.clubelivro.entidade.financeiro.InadimplenciaCategoria;
import br.org.crvnluz.editora.clubelivro.entidade.financeiro.InadimplenciaMensal;
import br.org.crvnluz.editora.clubelivro.infra.rest.BaseController;
import br.org.crvnluz.editora.clubelivro.repositorio.financeiro.InadimplenciaDAO;

//@RestController
//@RequestMapping("/inadimplencia")
public class InadimplenciaController extends BaseController {
/*
	@Autowired
	private InadimplenciaDAO dao;
	
	// MÉTODOS PÚBLICOS
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/pesquisa")
	public ResponseEntity getInadimplencia() {
		ResponseEntity response;
		
		try {
			List<Object[]> dadosMensais = dao.getInadimplenciaMensal(LocalDate.now());
			InadimplenciaMensal inadimplenciaMensal = new InadimplenciaMensal();
			
			if (!dadosMensais.isEmpty()) {
				inadimplenciaMensal.preencherValores(dadosMensais);
			}
			
			List<Object[]> dadosCategoria = dao.getInadimplenciaPorCategoria(LocalDate.now());
			InadimplenciaCategoria inadimplenciaCategoria = new InadimplenciaCategoria();
			
			if (!dadosCategoria.isEmpty()) {
				inadimplenciaCategoria.preencherValores(dadosCategoria);
			}
			
			Inadimplencia inadimplencia = new Inadimplencia();
			inadimplencia.setInadimplenciaCategoria(inadimplenciaCategoria);
			inadimplencia.setInadimplenciaMensal(inadimplenciaMensal);
			response = new ResponseEntity(inadimplencia, HttpStatus.OK);
			
		} catch (Throwable throwable) {
			response = getInternalServerErrorResponse(throwable);
		}
		
		return response;
	}
	*/
}
