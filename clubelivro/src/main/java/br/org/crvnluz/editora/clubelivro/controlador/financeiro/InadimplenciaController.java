package br.org.crvnluz.editora.clubelivro.controlador.financeiro;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller
public class InadimplenciaController {
	/*
	@GetMapping("/relatorios/inadimplencia")
	public ModelAndView iniciar() {
		ModelAndView model = new ModelAndView("/relatorios/inadimplencia");
		return model;
	}
	*/
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
